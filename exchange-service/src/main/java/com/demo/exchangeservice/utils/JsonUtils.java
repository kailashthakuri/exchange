package com.demo.exchangeservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public class JsonUtils {
    public static <T> List<T> jsonArrayToList(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(List.class, elementClass);
        return objectMapper.readValue(json, listType);
    }
}
