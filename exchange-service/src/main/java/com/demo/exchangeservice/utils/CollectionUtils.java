package com.demo.exchangeservice.utils;

import java.util.Collection;

public class CollectionUtils {

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }
}
