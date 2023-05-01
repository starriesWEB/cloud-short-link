package com.starry.controller.request;

import lombok.Data;

@Data
public class ShortLinkDelRequest {


    /**
     * 组
     */
    private Long groupId;

    /**
     * 映射id
     */
    private Long mappingId;


    /**
     * 短链码
     */
    private String code;

}