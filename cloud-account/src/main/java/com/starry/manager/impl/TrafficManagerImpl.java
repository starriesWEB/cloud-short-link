package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starry.manager.TrafficManager;
import com.starry.mapper.TrafficMapper;
import com.starry.model.TrafficDO;
import com.starry.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TrafficManagerImpl implements TrafficManager {


    private final TrafficMapper trafficMapper;

    @Override
    public int add(TrafficDO trafficDO) {
        return trafficMapper.insert(trafficDO);
    }


    @Override
    public IPage<TrafficDO> pageAvailable(int page, int size, Long accountNo) {
        Page<TrafficDO> pageInfo = new Page<>(page, size);
        String today = TimeUtil.format(new Date(), "yyyy-MM-dd");

        Page<TrafficDO> trafficDOPage = trafficMapper.selectPage(pageInfo,
                Wrappers.lambdaQuery(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .ge(TrafficDO::getExpiredDate, today)
                        .orderByDesc(TrafficDO::getGmtCreate)
        );
        return trafficDOPage;
    }

    @Override
    public TrafficDO findByIdAndAccountNo(Long trafficId, Long accountNo) {
        TrafficDO trafficDO = trafficMapper.selectOne(
                Wrappers.lambdaQuery(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .eq(TrafficDO::getId, trafficId)
        );
        return trafficDO;
    }



    @Override
    public boolean deleteExpireTraffic() {
        int rows = trafficMapper.delete(
                Wrappers.lambdaQuery(TrafficDO.class)
                        .ne(TrafficDO::getOutTradeNo, "free_init")
                        .le(TrafficDO::getExpiredDate, new Date())
        );
        log.info("删除过期流量包行数：rows={}", rows);
        return true;
    }

    /**
     * 查找未过期流量列表（不一定可用，可能超过次数）
     * <p>
     * select * from traffic where account_no =111 and (expired_date >= ? OR out_trade_no=free_init )
     *
     * @param accountNo
     * @return
     */
    @Override
    public List<TrafficDO> selectAvailableTraffics(Long accountNo) {
        String today = TimeUtil.format(new Date(), "yyyy-MM-dd");
        return trafficMapper.selectList(
                Wrappers.lambdaQuery(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .and(queryWrapper -> queryWrapper
                                .ge(TrafficDO::getExpiredDate, today)
                                .or()
                                .eq(TrafficDO::getOutTradeNo, "free_init")));
    }

    /**
     * 增加流量包使用次数
     *
     * update traffic set
     *  day_used = day_used + ?
     * where
     *  account_no = ?
     *  and id = ?
     *  and day_limit >= day_used + ?
     * limit 1
     * @param accountNo
     * @param trafficId
     * @param usedTimes
     * @return
     */
    @Override
    public int addDayUsedTimes(Long accountNo, Long trafficId, Integer usedTimes) {
        return trafficMapper.addDayUsedTimes(accountNo,trafficId,usedTimes);
    }

    /**
     * 恢复某个流量包使用次数，回滚流量包
     * update traffic set
     *  day_used = day_used - ?
     * where
     *  account_no = ?
     *  and id = ?
     *  and day_used - ? >= 0
     * limit 1
     * @param accountNo
     * @param trafficId
     * @param usedTimes
     * @return
     */
    @Override
    public int releaseUsedTimes(Long accountNo, Long trafficId, Integer usedTimes) {
        return trafficMapper.update(null,
                Wrappers.lambdaUpdate(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .eq(TrafficDO::getId, trafficId)
                        .setSql("day_used = day_used - " + usedTimes)
                        .ge(TrafficDO::getDayUsed, 0)
                        .last("limit 1")
        );
    }

    @Override
    public int batchUpdateUsedTimes(Long accountNo, List<Long> unUpdatedTrafficIds) {
        return trafficMapper.update(null,
                Wrappers.lambdaUpdate(TrafficDO.class)
                        .eq(TrafficDO::getAccountNo, accountNo)
                        .in(TrafficDO::getId, unUpdatedTrafficIds)
                        .set(TrafficDO::getDayUsed, 0)
        );
    }
}