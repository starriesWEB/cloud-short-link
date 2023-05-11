package com.starry.feign;

import com.starry.utils.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "cloud-link-service")
public interface ShortLinkFeignService {


    /**
     * 检查短链是否存在
     * @param shortLinkCode
     * @return
     */
    @GetMapping(value = "/api/link/v1/check",headers = {"rpc-token=${rpc.token}"})
    JsonData check(@RequestParam("shortLinkCode") String shortLinkCode);

}