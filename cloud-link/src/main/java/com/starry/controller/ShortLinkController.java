package com.starry.controller;

import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.controller.request.ShortLinkDelRequest;
import com.starry.controller.request.ShortLinkPageRequest;
import com.starry.controller.request.ShortLinkUpdateRequest;
import com.starry.service.ShortLinkService;
import com.starry.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/link/v1")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @PostMapping("add")
    public JsonData createShortLink(@RequestBody ShortLinkAddRequest request){
        return shortLinkService.createShortLink(request);
    }

    /**
     * 分页查找短链
     */

    @RequestMapping("page")
    public JsonData pageByGroupId(@RequestBody ShortLinkPageRequest request){
        Map<String,Object> result = shortLinkService.pageByGroupId(request);
        return JsonData.buildSuccess(result);
    }

    /**
     * 删除短链
     * @param request
     * @return
     */
    @PostMapping("del")
    public JsonData del(@RequestBody ShortLinkDelRequest request){
        return shortLinkService.del(request);
    }

    /**
     * 更新短链
     * @param request
     * @return
     */
    @PostMapping("update")
    public JsonData update(@RequestBody ShortLinkUpdateRequest request){
        return shortLinkService.update(request);
    }
}