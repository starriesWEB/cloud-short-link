package com.starry.controller.request;

import lombok.Data;

@Data
public class LinkGroupUpdateRequest {

    /**
     * 组id
     */
    private Long id;
    /**
     * 组名
     */
    private String title;
}