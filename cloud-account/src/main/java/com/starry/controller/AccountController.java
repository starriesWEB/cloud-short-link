package com.starry.controller;

import com.starry.enums.BizCodeEnum;
import com.starry.service.FileService;
import com.starry.utils.JsonData;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:49
 * @Description
 */
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/account")
public class AccountController {

    private final FileService fileService;

    @PostMapping("upload")
    public JsonData uploadUserImg(@RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserImg(file);
        return result != null ? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }
}
