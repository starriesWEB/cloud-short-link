package com.starry.controller;

import com.google.code.kaptcha.Producer;
import com.starry.service.NotifyService;
import com.starry.utils.JsonData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:49
 * @Description
 */
@Slf4j
@AllArgsConstructor
@RestController("/api/v1/notify")
public class NotifyController {

    private final NotifyService notifyService;
    private final Producer captchaProducer;

    @RequestMapping("send_code")
    public JsonData sendCode(String phone) {
        notifyService.sendSms(phone);
        return JsonData.buildSuccess();
    }

    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String captchaText = captchaProducer.createText();
        log.info("验证码内容:{}", captchaText);
        //存储redis,配置过期时间 TODO
        BufferedImage bufferedImage = captchaProducer.createImage(captchaText);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("获取流出错:{}", e.getMessage());
        }

    }

}

