package com.starry.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.starry.manager.AccountManager;
import com.starry.mapper.AccountMapper;
import com.starry.model.AccountDO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:45
 * @Description
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AccountManagerImpl implements AccountManager {

    private final AccountMapper accountMapper;

    @Override
    public int insert(AccountDO accountDO) {
        return accountMapper.insert(accountDO);
    }

    @Override
    public AccountDO findByPhone(String phone) {
        return accountMapper.selectOne(Wrappers.lambdaQuery(AccountDO.class).eq(AccountDO::getPhone, phone));
    }
}
