package com.starry.controller.request;

import lombok.Data;

@Data
public class ShortLinkPageRequest {


    /**
     * 组
     */
    private Long groupId;

    /**
     * 第几页
     */
    private int page;

    /**
     * 每页多少条
     */
    private int size;

}