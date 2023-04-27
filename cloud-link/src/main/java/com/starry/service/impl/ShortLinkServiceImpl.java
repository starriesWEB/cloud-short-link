package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.manager.ShortLinkManager;
import com.starry.mapper.ShortLinkMapper;
import com.starry.model.ShortLinkDO;
import com.starry.service.ShortLinkService;
import com.starry.vo.ShortLinkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class ShortLinkServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO>
    implements ShortLinkService{

    private final ShortLinkManager shortLinkManager;

    @Override
    public ShortLinkVO parseShortLinkCode(String shortLinkCode) {
        ShortLinkDO shortLinkDO = shortLinkManager.findByShortLinCode(shortLinkCode);
        if(shortLinkDO == null){
            return null;
        }
        ShortLinkVO shortLinkVO = new ShortLinkVO();
        BeanUtils.copyProperties(shortLinkDO,shortLinkVO);
        return shortLinkVO;
    }
}




