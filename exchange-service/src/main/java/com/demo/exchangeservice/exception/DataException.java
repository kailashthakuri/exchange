package com.demo.exchangeservice.exception;

import com.demo.exchangeservice.dto.common.IErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private IErrorCode iErrorCode;

    public DataException(String message, IErrorCode iErrorCode) {
        super(message);
        this.iErrorCode = iErrorCode;
    }
}
