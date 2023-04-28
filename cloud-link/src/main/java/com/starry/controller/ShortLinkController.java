package com.starry.controller;

import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.service.ShortLinkService;
import com.starry.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/link/v1")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping("add")
    public JsonData createShortLink(@RequestBody ShortLinkAddRequest request){

        JsonData jsonData = shortLinkService.createShortLink(request);

        return jsonData;
    }
}