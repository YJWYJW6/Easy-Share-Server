package com.yangjw.easyshare.framework.web.core.handler;

import com.yangjw.easyshare.framework.common.exception.ServiceException;
import com.yangjw.easyshare.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.yangjw.easyshare.framework.common.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务逻辑异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult<Object> handleServiceException(ServiceException ex) {
        log.warn("[业务异常] code={}, msg={}", ex.getCode(), ex.getMessage()); // warn 级别
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String msg = "参数校验失败";
        if (ex.getBindingResult().hasFieldErrors()) {
            msg = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        }
        log.warn("[参数校验异常] {}", msg); // info 级别
        return CommonResult.error(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), msg);
    }

    /**
     * 兜底异常（未知异常）
     */
    @ExceptionHandler(Exception.class)
    public CommonResult<Object> handleException(Exception ex) {
        log.error("[系统异常]", ex); // error 级别
        return CommonResult.error(GlobalErrorCodeConstants.ERROR.getCode(), "系统繁忙，请稍后再试");
    }
}
