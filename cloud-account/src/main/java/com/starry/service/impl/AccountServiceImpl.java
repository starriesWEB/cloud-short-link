package com.starry.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.starry.controller.request.AccountLoginRequest;
import com.starry.controller.request.AccountRegisterRequest;
import com.starry.enums.AuthTypeEnum;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.SendCodeEnum;
import com.starry.manager.AccountManager;
import com.starry.mapper.AccountMapper;
import com.starry.model.AccountDO;
import com.starry.model.LoginUser;
import com.starry.service.AccountService;
import com.starry.service.NotifyService;
import com.starry.utils.CommonUtil;
import com.starry.utils.IDUtil;
import com.starry.utils.JWTUtil;
import com.starry.utils.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends ServiceImpl<AccountMapper, AccountDO>
        implements AccountService {

    private final NotifyService notifyService;
    private final AccountManager accountManager;

    @Override
    public JsonData register(AccountRegisterRequest registerRequest) {

        boolean checkCode = false;
        //判断验证码
        if (StringUtils.isNotBlank(registerRequest.getPhone())) {
            checkCode = notifyService.checkCode(SendCodeEnum.USER_REGISTER, registerRequest.getPhone(), registerRequest.getCode());
        }
        //验证码错误
        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_ERROR);
        }
        AccountDO accountDO = new AccountDO();

        BeanUtils.copyProperties(registerRequest, accountDO);
        //认证级别
        accountDO.setAuth(AuthTypeEnum.DEFAULT.name());

        accountDO.setAccountNo(Long.valueOf(IDUtil.genSnowFlakeID().toString()));


        //设置密码 秘钥 盐
        accountDO.setSecret("$1$" + CommonUtil.getStringNumRandom(8));
        String cryptPwd = Md5Crypt.md5Crypt(registerRequest.getPwd().getBytes(), accountDO.getSecret());
        accountDO.setPwd(cryptPwd);

        int rows = accountManager.insert(accountDO);
        log.info("rows:{},注册成功:{}", rows, accountDO);

        //用户注册成功，发放福利 TODO
        userRegisterInitTask(accountDO);

        return JsonData.buildSuccess();

    }

    @Override
    public JsonData login(AccountLoginRequest request) {
        AccountDO accountDO = accountManager.findByPhone(request.getPhone());
        if (accountDO != null) {
            String md5Crypt = Md5Crypt.md5Crypt(request.getPwd().getBytes(), accountDO.getSecret());
            if (md5Crypt.equalsIgnoreCase(accountDO.getPwd())) {
                LoginUser loginUser = LoginUser.builder().build();
                BeanUtils.copyProperties(accountDO, loginUser);
                return JsonData.buildSuccess(JWTUtil.genJsonWebTokne(loginUser));
            } else {
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    /**
     * 用户初始化，发放福利：流量包 TODO
     *
     * @param accountDO
     */
    private void userRegisterInitTask(AccountDO accountDO) {

    }
}




