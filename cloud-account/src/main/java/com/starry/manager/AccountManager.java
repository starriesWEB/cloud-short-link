package com.starry.manager;

import com.starry.model.AccountDO;

/**
 *
 */
public interface AccountManager {


    int insert(AccountDO accountDO);


    AccountDO findByPhone(String phone);

}
