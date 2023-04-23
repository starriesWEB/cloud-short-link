package com.starry.service.impl;

import com.starry.component.SmsComponent;
import com.starry.service.NotifyService;
import com.starry.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/23 14:12
 * @Description
 */
@Slf4j
@Service
@AllArgsConstructor
public class NotifyServiceImpl implements NotifyService {

    private final SmsComponent smsComponent;

    /**
     * 发送短信
     */
    @Override
    @Async("threadPoolTaskExecutor")
    public void sendSms(String phone) {
        smsComponent.sendSms(phone, CommonUtil.getRandomCode(4));
    }
}
