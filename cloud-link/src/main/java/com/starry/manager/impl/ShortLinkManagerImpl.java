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
        return shortLinkMapper.selectOne(
                Wrappers.lambdaQuery(ShortLinkDO.class)
                        .eq(ShortLinkDO::getCode, shortLinkCode)
                        .eq(ShortLinkDO::getDel, 0)
        );
    }

    @Override
    public int del(ShortLinkDO shortLinkDO) {
        return shortLinkMapper.update(null,
                Wrappers.lambdaUpdate(ShortLinkDO.class)
                        .eq(ShortLinkDO::getCode, shortLinkDO.getCode())
                        .eq(ShortLinkDO::getAccountNo, shortLinkDO.getAccountNo())
                        .set(ShortLinkDO::getDel, 1));
    }

    @Override
    public int update(ShortLinkDO shortLinkDO) {
        return shortLinkMapper.update(null,
                Wrappers.lambdaUpdate(ShortLinkDO.class)
                .eq(ShortLinkDO::getCode, shortLinkDO.getCode())
                .eq(ShortLinkDO::getAccountNo, shortLinkDO.getAccountNo())
                .eq(ShortLinkDO::getDel, 0)
                .set(ShortLinkDO::getTitle, shortLinkDO.getTitle())
                .set(ShortLinkDO::getDomain, shortLinkDO.getDomain())
        );
    }
}