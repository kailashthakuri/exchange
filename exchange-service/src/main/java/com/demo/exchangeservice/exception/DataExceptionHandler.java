package com.demo.exchangeservice.exception;

import com.demo.exchangeservice.dto.common.ErrorCodes;
import com.demo.exchangeservice.dto.common.ErrorDTO;
import com.demo.exchangeservice.dto.common.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.UnexpectedTypeException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DataExceptionHandler {

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            UnexpectedTypeException.class
    })
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            Object errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return buildResponseEntity(
                ErrorDTO.getError(ErrorCodes.INVALID_PARAMETER, errors, null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataException.class)
    protected ResponseEntity<Object> handleDataServiceException(DataException ex) {
        return buildResponseEntity(
                ErrorDTO.getError(ErrorCodes.DATA_NOT_FOUND, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        return buildResponseEntity(
                ErrorDTO.getError(ErrorCodes.MESSAGE_NOT_READABLE, ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return buildResponseEntity(
                ErrorDTO.getError(ErrorCodes.INVALID_PARAMETER, Collections.singletonMap(ex.getName(), "invalid type or format"), null),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleClientError(HttpClientErrorException ex) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(ex.getStatusCode().value());
        errorDTO.setTitle(ex.getStatusText());
        errorDTO.setDetail(ex.getMessage());
        return buildResponseEntity(errorDTO, ex.getStatusCode());
    }

    // default exception handler
    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleAllOtherException(Throwable ex) {
        return buildResponseEntity(
                ErrorDTO.getError(ErrorCodes.INTERNAL_SERVER_ERROR, null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


    private ResponseEntity<Object> buildResponseEntity(ErrorDTO apiError, HttpStatus status) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setError(apiError);
        return new ResponseEntity<>(responseDTO, status);
    }
}

