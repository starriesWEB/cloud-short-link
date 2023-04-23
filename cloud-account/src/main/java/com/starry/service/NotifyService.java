package com.starry.service;

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
    void sendSms(String phone);
}
