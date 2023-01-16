package com.demo.exchangeservice.dto.common;

import org.springframework.http.HttpStatus;

public interface IErrorCode {
    String getCode();
    String getTitle();
    HttpStatus getStatus();
}

