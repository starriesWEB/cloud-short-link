package com.starry.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.starry.properties.OSSProperties;
import com.starry.service.FileService;
import com.starry.utils.CommonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final OSSProperties ossProperties;

    private static OSS ossClient;

    @PostConstruct
    public void init() {
        ossClient = new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    @PreDestroy
    public void close() {
        ossClient.shutdown();
    }

    @Override
    public String uploadUserImg(MultipartFile file) {
        String bucketName = ossProperties.getBucketName();
        String endpoint = ossProperties.getEndpoint();

        String originalFilename = file.getOriginalFilename();

        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //user/2022/12/12/uuid.png
        String folder = pattern.format(ldt);
        String fileName = CommonUtil.generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = "user/" + folder + "/" + fileName + extension;

        try {
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, newFilename, file.getInputStream());
            if (putObjectResult != null) {
                return "https://" + bucketName + "." + endpoint + "/" + newFilename;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        }
        return null;
    }
}
