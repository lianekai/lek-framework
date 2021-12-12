package com.lianekai.util.collection;

import java.util.Collection;
import java.util.Map;

/**
 * Collection工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2021/12/12 00:52
 */
public class CollectionUtils {

    private CollectionUtils(){}

    public static boolean isEmpty( Collection<?> collection) {
        return (collection == null || collection.isEmpty());
    }

    public static boolean isNotEmpty( Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isEmpty( Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }
}
