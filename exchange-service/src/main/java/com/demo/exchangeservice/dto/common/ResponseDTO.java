package com.demo.exchangeservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;


@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private Object data;
    private ErrorDTO error;
    private Map<String, Object> meta = new LinkedHashMap<>();


    public ResponseDTO(Object data) {
        this.data = data;
    }

    public static ResponseDTO errorResponse(Integer errorCode, String errorMessage) {
        ResponseDTO responseDTO = new ResponseDTO();
        return responseDTO;
    }

    public static ResponseDTO successResponse(Object data) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.data = data;
        return responseDTO;
    }

    public static ResponseDTO successWithMetaResponse(Object data, Map<String, Object> meta) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.data = data;
        responseDTO.meta = meta;
        return responseDTO;
    }
}
