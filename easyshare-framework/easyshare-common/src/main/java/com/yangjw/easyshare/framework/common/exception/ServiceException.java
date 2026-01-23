package com.yangjw.easyshare.framework.common.exception;

import lombok.Getter;

/**
 * 业务逻辑异常
 */
@Getter
public class ServiceException extends RuntimeException {

    private final Integer code;

    public ServiceException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServiceException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }
}
