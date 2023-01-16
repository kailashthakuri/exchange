package com.demo.exchangeservice.dto.common;

import org.springframework.http.HttpStatus;


public enum ErrorCodes implements IErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER", "Invalid request parameters", HttpStatus.BAD_REQUEST),
    MESSAGE_NOT_READABLE("MESSAGE_NOT_READABLE", "Invalid request format or type", HttpStatus.BAD_REQUEST),
    DATA_NOT_FOUND("DATA_NOT_FOUND", "Requested data not found", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Server can not process request at a moment. try again!", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String title;
    private final HttpStatus status;

    ErrorCodes(String code, String title, HttpStatus status) {
        this.code = code;
        this.title = title;
        this.status = status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }
}
