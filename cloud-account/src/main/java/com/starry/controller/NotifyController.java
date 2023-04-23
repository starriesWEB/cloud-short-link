package com.starry.controller;

import com.starry.service.NotifyService;
import com.starry.utils.JsonData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:49
 * @Description
 */
@RestController("/api/v1/notify")
@AllArgsConstructor
public class NotifyController {

    private final NotifyService notifyService;

    @RequestMapping("send_code")
    public JsonData sendCode(String phone) {
        notifyService.sendSms(phone);
        return JsonData.buildSuccess();
    }

}
