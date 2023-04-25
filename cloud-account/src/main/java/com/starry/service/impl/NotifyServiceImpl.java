package com.starry.service.impl;

import com.starry.component.SmsComponent;
import com.starry.constant.RedisKey;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.SendCodeEnum;
import com.starry.service.NotifyService;
import com.starry.utils.CheckUtil;
import com.starry.utils.CommonUtil;
import com.starry.utils.JsonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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
    private final StringRedisTemplate redisTemplate;
    private static final int CODE_EXPIRED = 60 * 1000 * 10;


    /**
     * 发送短信
     */
    @Override
    public JsonData sendSms(SendCodeEnum sendCodeEnum, String to) {
        String cacheKey = String.format(RedisKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        // cacheValue: 666666_1682244011
        if (StringUtils.isNotBlank(cacheValue)) {
            long sendTime = Long.parseLong(cacheValue.split("_")[1]);
            // if current time - send time < 60s, return
            long remainingTime = CommonUtil.getCurrentTimestamp() - sendTime;
            if (remainingTime < (1000 * 60)) {
                log.info("重复发送短信验证码，时间间隔:{}秒", remainingTime / 1000);
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }

        String code = CommonUtil.getRandomCode(6);
        cacheValue = code + "_" + CommonUtil.getCurrentTimestamp();
        redisTemplate.opsForValue().set(cacheKey, cacheValue, CODE_EXPIRED, TimeUnit.MILLISECONDS);

        if (CheckUtil.isEmail(to)) {
        } else if (CheckUtil.isPhone(to)) {
            smsComponent.sendSms(to, code);
        }
        return JsonData.buildSuccess();
    }

    @Override
    public boolean checkCode(SendCodeEnum sendCodeEnum, String to, String code) {
        String cacheKey = String.format(RedisKey.CHECK_CODE_KEY, sendCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            String cacheCode = cacheValue.split("_")[0];
            if (cacheCode.equalsIgnoreCase(code)) {
                //删除验证码
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
