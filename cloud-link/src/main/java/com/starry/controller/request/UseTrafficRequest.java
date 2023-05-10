package com.starry.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UseTrafficRequest {


    /**
     * 账号
     */
    private Long accountNo;

    /**
     * 业务id, 短链码
     */
    private String bizId;


}