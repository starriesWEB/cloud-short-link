package com.starry.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsProperties {

    private String accessKeyId;
    private String accessKeySecret;
    private String region;
    private String endpoint;
    private String signName;
    private String templateCode;
    private String templateParam;

}