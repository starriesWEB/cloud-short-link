package com.starry.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/24 15:34
 * @Description
 */
public interface FileService {

    /**
     * 文件上传
     * @param file
     * @return
     */
    String uploadUserImg(MultipartFile file);
}

