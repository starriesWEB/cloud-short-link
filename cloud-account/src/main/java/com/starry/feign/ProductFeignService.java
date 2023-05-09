package com.starry.feign;

import com.starry.utils.JsonData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cloud-shop-service")
public interface ProductFeignService {


    /**
     * 获取流量包商品详情
     * @param productId
     * @return
     */
    @GetMapping("/api/product/v1/detail/{product_id}")
    JsonData detail(@PathVariable("product_id") long productId);

}