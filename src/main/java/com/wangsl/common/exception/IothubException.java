package com.wangsl.common.exception;

import com.wangsl.common.api.IErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义异常
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class IothubException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Boolean json;

    public IothubException(IothubExceptionMap exception, Boolean json) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.message = exception.getMessage();
        this.json = json;
    }

    public IothubException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.json = true;
    }

    public IothubException(String message) {
        super(message);
        this.code = "500";
        this.message = message;
        this.json = false;
    }

    public IothubException(IErrorCode exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

}
