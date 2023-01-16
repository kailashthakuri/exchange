package com.demo.exchangeservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@Getter
@Setter
@Builder
@Data
public class ErrorDTO {
    private Long timestamp;
    private String code;
    private Integer status;
    private String title;
    private String detail;
    private Map<String, Object> errors;

    public ErrorDTO() {
        this.timestamp = Instant.now().toEpochMilli();
    }


    public static ErrorDTO getError(IErrorCode iErrorCode, Map<String, Object> errors, String detail) {
        ErrorDTO errorDTO = getError(iErrorCode, detail);
        errorDTO.setErrors(errors);
        return errorDTO;
    }

    public static ErrorDTO getError(IErrorCode iErrorCode, String detail) {
        return ErrorDTO.builder()
                .timestamp(generateTimeStamp())
                .title(iErrorCode.getTitle())
                .code(iErrorCode.getCode())
                .detail(detail)
                .status(iErrorCode.getStatus().value())
                .build();
    }

    public static Long generateTimeStamp() {
        return Instant.now().toEpochMilli();
    }
}
