package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.controller.request.LinkGroupAddRequest;
import com.starry.controller.request.LinkGroupUpdateRequest;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.LinkGroupManager;
import com.starry.mapper.LinkGroupMapper;
import com.starry.model.LinkGroupDO;
import com.starry.service.LinkGroupService;
import com.starry.vo.LinkGroupVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class LinkGroupServiceImpl extends ServiceImpl<LinkGroupMapper, LinkGroupDO>
        implements LinkGroupService {

    private final LinkGroupManager linkGroupManager;

    @Override
    public int add(LinkGroupAddRequest addRequest) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();

        LinkGroupDO linkGroupDO = new LinkGroupDO();
        linkGroupDO.setTitle(addRequest.getTitle());
        linkGroupDO.setAccountNo(accountNo);

        return linkGroupManager.add(linkGroupDO);
    }


    @Override
    public int del(Long groupId) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        return linkGroupManager.del(groupId, accountNo);

    }

    @Override
    public LinkGroupVO detail(Long groupId) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        LinkGroupDO linkGroupDO = linkGroupManager.detail(groupId, accountNo);
        LinkGroupVO linkGroupVO = new LinkGroupVO();
        BeanUtils.copyProperties(linkGroupDO, linkGroupVO);
        return linkGroupVO;
    }

    @Override
    public List<LinkGroupVO> listAllGroup() {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();
        List<LinkGroupDO> linkGroupDOList = linkGroupManager.listAllGroup(accountNo);

        return linkGroupDOList.stream().map(obj -> {
            LinkGroupVO linkGroupVO = new LinkGroupVO();
            BeanUtils.copyProperties(obj, linkGroupVO);
            return linkGroupVO;
        }).collect(Collectors.toList());
    }


    @Override
    public int updateById(LinkGroupUpdateRequest request) {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();

        LinkGroupDO linkGroupDO = new LinkGroupDO();
        linkGroupDO.setTitle(request.getTitle());
        linkGroupDO.setId(request.getId());
        linkGroupDO.setAccountNo(accountNo);

        return linkGroupManager.updateById(linkGroupDO);
    }


}




