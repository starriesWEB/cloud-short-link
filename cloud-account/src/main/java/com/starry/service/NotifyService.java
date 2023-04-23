package com.starry.service;

import com.starry.enums.SendCodeEnum;
import com.starry.utils.JsonData;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/23 14:12
 * @Description
 */
public interface NotifyService {

    /**
     * 发送短信
     */
    JsonData sendSms(SendCodeEnum sendCodeEnum, String to);
}
