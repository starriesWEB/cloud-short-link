package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.starry.manager.LinkGroupManager;
import com.starry.mapper.LinkGroupMapper;
import com.starry.model.LinkGroupDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LinkGroupManagerImpl implements LinkGroupManager {

    private final LinkGroupMapper linkGroupMapper;

    @Override
    public int add(LinkGroupDO linkGroupDO) {
        return linkGroupMapper.insert(linkGroupDO);
    }

    @Override
    public int del(Long groupId, Long accountNo) {
        return linkGroupMapper.delete(Wrappers.lambdaQuery(LinkGroupDO.class).eq(LinkGroupDO::getId, groupId).eq(LinkGroupDO::getAccountNo, accountNo));
    }

    @Override
    public LinkGroupDO detail(Long groupId, Long accountNo) {
        return linkGroupMapper.selectOne(Wrappers.lambdaQuery(LinkGroupDO.class).eq(LinkGroupDO::getId, groupId).eq(LinkGroupDO::getAccountNo, accountNo));
    }

    @Override
    public List<LinkGroupDO> listAllGroup(Long accountNo) {
        return linkGroupMapper.selectList(Wrappers.lambdaQuery(LinkGroupDO.class).eq(LinkGroupDO::getAccountNo, accountNo));
    }

    @Override
    public int updateById(LinkGroupDO linkGroupDO) {
        return linkGroupMapper.update(null,
                Wrappers.lambdaUpdate(LinkGroupDO.class)
                        .eq(LinkGroupDO::getId, linkGroupDO.getId())
                        .eq(LinkGroupDO::getAccountNo, linkGroupDO.getAccountNo())
                        .set(LinkGroupDO::getTitle, linkGroupDO.getTitle())
        );
    }
}
