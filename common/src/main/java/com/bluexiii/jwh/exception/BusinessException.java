package com.bluexiii.jwh.exception;

/**
 * 通用业务逻辑异常
 * Created by bluexiii on 16/8/24.
 */
public class BusinessException extends RuntimeException {
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
