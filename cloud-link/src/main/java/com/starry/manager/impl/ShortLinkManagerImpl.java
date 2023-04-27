package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.starry.manager.ShortLinkManager;
import com.starry.mapper.ShortLinkMapper;
import com.starry.model.ShortLinkDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ShortLinkManagerImpl implements ShortLinkManager {

    private final ShortLinkMapper shortLinkMapper;

    @Override
    public int addShortLink(ShortLinkDO shortLinkDO) {
        return shortLinkMapper.insert(shortLinkDO);
    }

    @Override
    public ShortLinkDO findByShortLinCode(String shortLinkCode) {
        return shortLinkMapper.selectOne(Wrappers.lambdaQuery(ShortLinkDO.class).eq(ShortLinkDO::getCode, shortLinkCode));
    }

    @Override
    public int del(String shortLinkCode, Long accountNo) {
        ShortLinkDO shortLinkDO = new ShortLinkDO();
        shortLinkDO.setDel(1);
        return shortLinkMapper.update(shortLinkDO,
                Wrappers.lambdaQuery(ShortLinkDO.class)
                        .eq(ShortLinkDO::getCode, shortLinkCode)
                        .eq(ShortLinkDO::getAccountNo, accountNo)
        );
    }
}