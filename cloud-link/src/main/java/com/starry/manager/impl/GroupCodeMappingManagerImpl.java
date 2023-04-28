package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.starry.enums.ShortLinkStateEnum;
import com.starry.manager.GroupCodeMappingManager;
import com.starry.mapper.GroupCodeMappingMapper;
import com.starry.model.GroupCodeMappingDO;
import com.starry.vo.GroupCodeMappingVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class GroupCodeMappingManagerImpl implements GroupCodeMappingManager {

    private final GroupCodeMappingMapper groupCodeMappingMapper;

    @Override
    public GroupCodeMappingDO findByGroupIdAndMappingId(Long mappingId, Long accountNo, Long groupId) {
        return groupCodeMappingMapper.selectOne(Wrappers.lambdaQuery(GroupCodeMappingDO.class).eq(GroupCodeMappingDO::getGroupId, mappingId).eq(GroupCodeMappingDO::getAccountNo, accountNo).eq(GroupCodeMappingDO::getGroupId, groupId));
    }

    @Override
    public int add(GroupCodeMappingDO groupCodeMappingDO) {
        return groupCodeMappingMapper.insert(groupCodeMappingDO);
    }

    @Override
    public int del(String shortLinkCode, Long accountNo, Long groupId) {
        return groupCodeMappingMapper.update(null, Wrappers.lambdaUpdate(GroupCodeMappingDO.class)
                .eq(GroupCodeMappingDO::getCode, shortLinkCode)
                .eq(GroupCodeMappingDO::getAccountNo, accountNo)
                .eq(GroupCodeMappingDO::getGroupId, groupId)
                .set(GroupCodeMappingDO::getDel, 1));
    }

    @Override
    public Map<String, Object> pageShortLinkByGroupId(Integer page, Integer size, Long accountNo, Long groupId) {

        Page<GroupCodeMappingDO> pageInfo = new Page<>(page, size);


        Page<GroupCodeMappingDO> groupCodeMappingDOPage = groupCodeMappingMapper.selectPage(pageInfo, Wrappers.lambdaUpdate(GroupCodeMappingDO.class)
                .eq(GroupCodeMappingDO::getAccountNo, accountNo)
                .eq(GroupCodeMappingDO::getGroupId, groupId));

        Map<String, Object> pageMap = new HashMap<>(3);

        pageMap.put("total_record", groupCodeMappingDOPage.getTotal());
        pageMap.put("total_page", groupCodeMappingDOPage.getPages());
        pageMap.put("current_data", groupCodeMappingDOPage.getRecords()
                .stream().map(this::beanProcess).collect(Collectors.toList()));
        return pageMap;
    }

    @Override
    public int updateGroupCodeMappingState(Long accountNo, Long groupId, String shortLinkCode, ShortLinkStateEnum shortLinkStateEnum) {

        return groupCodeMappingMapper.update(null, Wrappers.lambdaUpdate(GroupCodeMappingDO.class)
                .eq(GroupCodeMappingDO::getCode, shortLinkCode)
                .eq(GroupCodeMappingDO::getAccountNo, accountNo)
                .eq(GroupCodeMappingDO::getGroupId, groupId)
                .set(GroupCodeMappingDO::getState, shortLinkStateEnum.name()));
    }


    private GroupCodeMappingVO beanProcess(GroupCodeMappingDO groupCodeMappingDO) {
        GroupCodeMappingVO groupCodeMappingVO = new GroupCodeMappingVO();
        BeanUtils.copyProperties(groupCodeMappingDO, groupCodeMappingVO);

        return groupCodeMappingVO;
    }

}