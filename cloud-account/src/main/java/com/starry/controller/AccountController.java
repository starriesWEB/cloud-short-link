package com.starry.controller;

import com.starry.controller.request.AccountLoginRequest;
import com.starry.controller.request.AccountRegisterRequest;
import com.starry.enums.BizCodeEnum;
import com.starry.service.AccountService;
import com.starry.service.FileService;
import com.starry.utils.JsonData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author starry
 * @version 1.0
 * @date 2023/4/22 22:49
 * @Description
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/account/v1")
public class AccountController {

    private final FileService fileService;
    private final AccountService accountService;

    @PostMapping("upload")
    public JsonData uploadUserImg(@RequestPart("file") MultipartFile file) {
        String result = fileService.uploadUserImg(file);
        return result != null ? JsonData.buildSuccess(result) : JsonData.buildResult(BizCodeEnum.FILE_UPLOAD_USER_IMG_FAIL);
    }

    @PostMapping("register")
    public JsonData register(@RequestBody AccountRegisterRequest registerRequest){
        return accountService.register(registerRequest);
    }

    @PostMapping("login")
    public JsonData login(@RequestBody AccountLoginRequest request){
        return accountService.login(request);
    }
}
