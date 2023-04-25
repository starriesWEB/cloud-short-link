package com.starry.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.starry.controller.request.AccountLoginRequest;
import com.starry.controller.request.AccountRegisterRequest;
import com.starry.model.AccountDO;
import com.starry.utils.JsonData;

/**
 *
 */
public interface AccountService extends IService<AccountDO> {

    JsonData register(AccountRegisterRequest registerRequest);

    JsonData login(AccountLoginRequest request);
}
