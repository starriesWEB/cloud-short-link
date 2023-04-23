package com.starry.exception;


import com.starry.utils.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
//@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e) {

        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("[系统异常]{}", ExceptionUtils.getStackTrace(e));
            return JsonData.buildCodeAndMsg(bizException.getCode(), bizException.getMsg());
        } else {
            log.error("[系统异常]{}", ExceptionUtils.getStackTrace(e));
            return JsonData.buildError("系统异常");
        }

    }

}
