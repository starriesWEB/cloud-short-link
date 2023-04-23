package com.starry.controller;

import com.google.code.kaptcha.Producer;
import com.starry.controller.request.SendCodeRequest;
import com.starry.enums.BizCodeEnum;
import com.starry.enums.SendCodeEnum;
import com.starry.service.NotifyService;
import com.starry.utils.CommonUtil;
import com.starry.utils.JsonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:49
 * @Description
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/notify")
public class NotifyController {

    private final NotifyService notifyService;
    private final Producer captchaProducer;
    private final StringRedisTemplate stringRedisTemplate;
    private static final long CAPTCHA_CODE_EXPIRED = 1000 * 10 * 60;

    @PostMapping("send_code")
    public JsonData sendCode(HttpServletRequest request, @RequestBody SendCodeRequest sendCodeRequest) {
        String imgTextKey = getCaptchaKey(request);
        String imgTextValue = stringRedisTemplate.opsForValue().get(imgTextKey);
        String captcha = sendCodeRequest.getCaptcha();
        if (imgTextValue != null && imgTextValue.equalsIgnoreCase(captcha)) {
            stringRedisTemplate.delete(imgTextKey);
            return notifyService.sendSms(SendCodeEnum.USER_REGISTER, sendCodeRequest.getTo());
        } else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA_ERROR);
        }

    }

    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = captchaProducer.createText();
        log.info("图片验证码内容:{}", captchaText);

        stringRedisTemplate.opsForValue().set(getCaptchaKey(request), captchaText, CAPTCHA_CODE_EXPIRED, TimeUnit.MILLISECONDS);

        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("获取流出错:{}", e.getMessage());
        }

    }

    private String getCaptchaKey(HttpServletRequest request) {
        String ip = CommonUtil.getIpAddr(request);
        String userAgent = request.getHeader("User-Agent");
        String key = "account-service:captcha:" + CommonUtil.MD5(ip + userAgent);
        log.info("验证码key:{}", key);
        return key;
    }

}

