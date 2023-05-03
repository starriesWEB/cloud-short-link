package com.starry.controller;

import com.starry.service.ProductService;
import com.starry.utils.JsonData;
import com.starry.vo.ProductVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 查看商品列表接口
     * @return
     */
    @GetMapping("list")
    public JsonData list(){
        List<ProductVO> list = productService.productDOList();
        return JsonData.buildSuccess(list);

    }


    /**
     * 查看商品详情
     * @param productId
     * @return
     */
    @GetMapping("detail/{product_id}")
    public JsonData detail(@PathVariable("product_id") long productId){
        ProductVO productVO = productService.findDetailById(productId);
        return JsonData.buildSuccess(productVO);
    }


}