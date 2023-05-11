package com.starry.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseTrafficRequest {

    private Long accountNo;
    /**
     * 业务id, 短链码
     */
    private String bizId;
}