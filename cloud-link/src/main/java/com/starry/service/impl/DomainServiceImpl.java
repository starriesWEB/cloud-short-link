package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.interceptor.LoginInterceptor;
import com.starry.manager.DomainManager;
import com.starry.mapper.DomainMapper;
import com.starry.model.DomainDO;
import com.starry.service.DomainService;
import com.starry.vo.DomainVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DomainServiceImpl extends ServiceImpl<DomainMapper, DomainDO> implements DomainService {

    private final DomainManager domainManager;

    @Override
    public List<DomainVO> listAll() {
        Long accountNo = LoginInterceptor.threadLocal.get().getAccountNo();

        List<DomainDO> customDomainList = domainManager.listCustomDomain(accountNo);
        List<DomainDO> officialDomainList = domainManager.listOfficialDomain();

        customDomainList.addAll(officialDomainList);

        return customDomainList.stream().map(this::beanProcess).collect(Collectors.toList());
    }


    private DomainVO beanProcess(DomainDO domainDO){
        DomainVO domainVO = new DomainVO();
        BeanUtils.copyProperties(domainDO,domainVO);
        return domainVO;

    }

}




