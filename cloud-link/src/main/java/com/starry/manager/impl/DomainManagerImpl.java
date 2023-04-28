package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.starry.enums.DomainTypeEnum;
import com.starry.manager.DomainManager;
import com.starry.mapper.DomainMapper;
import com.starry.model.DomainDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DomainManagerImpl implements DomainManager {

    private final DomainMapper domainMapper;

    @Override
    public DomainDO findById(Long id, Long accountNo) {
        return domainMapper.selectOne(Wrappers.lambdaQuery(DomainDO.class).eq(DomainDO::getId, id).eq(DomainDO::getAccountNo, accountNo));
    }

    @Override
    public DomainDO findByDomainTypeAndID(Long id, DomainTypeEnum domainTypeEnum) {
        return domainMapper.selectOne(Wrappers.lambdaQuery(DomainDO.class).eq(DomainDO::getId, id).eq(DomainDO::getDomainType, domainTypeEnum.name()));
    }

    @Override
    public int addDomain(DomainDO domainDO) {
        return domainMapper.insert(domainDO);
    }


    @Override
    public List<DomainDO> listOfficialDomain() {
        return domainMapper.selectList(Wrappers.lambdaQuery(DomainDO.class).eq(DomainDO::getDomainType, DomainTypeEnum.OFFICIAL.name()));
    }

    @Override
    public List<DomainDO> listCustomDomain(Long accountNo) {
        return domainMapper.selectList(Wrappers.lambdaQuery(DomainDO.class).eq(DomainDO::getDomainType, DomainTypeEnum.CUSTOM.name()).eq(DomainDO::getAccountNo, accountNo));
    }
}
