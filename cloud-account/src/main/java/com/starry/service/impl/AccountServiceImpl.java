package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.model.AccountDO;
import com.starry.service.AccountService;
import com.starry.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDO>
    implements AccountService{

}




