package com.dmaksimn.store.exception;

import com.dmaksimn.store.enums.ResultEnum;

public class AppException extends RuntimeException {
    private  Integer code;

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
