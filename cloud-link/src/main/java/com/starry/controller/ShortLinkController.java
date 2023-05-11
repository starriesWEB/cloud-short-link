package com.starry.controller;

import com.starry.controller.request.ShortLinkAddRequest;
import com.starry.controller.request.ShortLinkDelRequest;
import com.starry.controller.request.ShortLinkPageRequest;
import com.starry.controller.request.ShortLinkUpdateRequest;
import com.starry.service.ShortLinkService;
import com.starry.utils.JsonData;
import com.starry.vo.ShortLinkVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/link/v1")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    @Value("${rpc.token}")
    private String rpcToken;

    @GetMapping("check")
    public JsonData check(@RequestParam("shortLinkCode") String shortLinkCode, @RequestHeader("rpc-token") String rpcToken) {
        if (this.rpcToken.equalsIgnoreCase(rpcToken)) {
            ShortLinkVO shortLinkVO = shortLinkService.parseShortLinkCode(shortLinkCode);
            return shortLinkVO == null ? JsonData.buildError("短链不存在") : JsonData.buildSuccess();
        } else {
            return JsonData.buildError("非法访问");
        }

    }

    @PostMapping("add")
    public JsonData createShortLink(@RequestBody ShortLinkAddRequest request) {
        return shortLinkService.createShortLink(request);
    }

    /**
     * 分页查找短链
     */

    @RequestMapping("page")
    public JsonData pageByGroupId(@RequestBody ShortLinkPageRequest request) {
        Map<String, Object> result = shortLinkService.pageByGroupId(request);
        return JsonData.buildSuccess(result);
    }

    /**
     * 删除短链
     *
     * @param request
     * @return
     */
    @PostMapping("del")
    public JsonData del(@RequestBody ShortLinkDelRequest request) {
        return shortLinkService.del(request);
    }

    /**
     * 更新短链
     *
     * @param request
     * @return
     */
    @PostMapping("update")
    public JsonData update(@RequestBody ShortLinkUpdateRequest request) {
        return shortLinkService.update(request);
    }
}