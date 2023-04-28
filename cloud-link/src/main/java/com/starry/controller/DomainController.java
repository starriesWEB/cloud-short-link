package com.starry.controller;

import com.starry.service.DomainService;
import com.starry.utils.JsonData;
import com.starry.vo.DomainVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/domain/v1")
@RequiredArgsConstructor
public class DomainController {


    private final DomainService domainService;

    /**
     * 列举全部可用域名列表
     * @return
     */
    @GetMapping("list")
    public JsonData listAll(){
        List<DomainVO> list = domainService.listAll();
        return JsonData.buildSuccess(list);
    }


}