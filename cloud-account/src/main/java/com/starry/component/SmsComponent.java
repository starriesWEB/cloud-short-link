package com.starry.component;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.core.http.HttpClient;
import com.aliyun.httpcomponent.httpclient.ApacheAsyncHttpClientBuilder;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.starry.properties.SmsProperties;
import com.starry.utils.JsonUtil;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/23 14:14
 * @Description
 */
@Slf4j
@Component
public class SmsComponent {

    @Resource
    private SmsProperties smsProperties;

    private static final HttpClient httpClient;
    private static final String CODE_OK = "OK";
    private static AsyncClient client;

    static {
        httpClient = new ApacheAsyncHttpClientBuilder()
                .connectionTimeout(Duration.ofSeconds(10))
                .responseTimeout(Duration.ofSeconds(10))
                .maxConnections(128)
                .maxIdleTimeOut(Duration.ofSeconds(50))
                .ignoreSSL(true)
                .build();
    }

    @PostConstruct
    private void init() {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(smsProperties.getAccessKeyId())
                .accessKeySecret(smsProperties.getAccessKeySecret())
                .build());
        client = AsyncClient.builder()
                .region(smsProperties.getRegion())
                .httpClient(httpClient)
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride(smsProperties.getEndpoint())
                )
                .build();
    }

    @PreDestroy
    private void close() {
        client.close();
    }

    public void sendSms(String phone, String code) {
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .phoneNumbers(phone)
                .signName(smsProperties.getSignName())
                .templateCode(smsProperties.getTemplateCode())
                .templateParam(String.format(smsProperties.getTemplateParam(), code))
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        response.thenAccept(resp -> {
                    if (log.isDebugEnabled()) {
                        log.debug("{}短信返回值: [{}]", phone, JsonUtil.obj2Json(resp.getBody()));
                    }
                    if (CODE_OK.equals(resp.getBody().getCode())) {
                        log.info("发送短信成功: phoneNumber[{}], code[{}]", phone, code);
                    } else {
                        log.error("发送短信失败: [{}], phoneNumber[{}], code[{}]", JsonUtil.obj2Json(resp.getBody()), phone, code);
                    }
                })
                .exceptionally(throwable -> {
                    log.error("发送短信失败: {}", throwable.getMessage());
                    return null;
                });
    }
}
