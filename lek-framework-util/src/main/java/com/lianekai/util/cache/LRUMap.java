package com.lianekai.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于LinkedHashMap实现，最近访问优先的LRU缓存  LRU是Least Recently Used的缩写--最近最少使用，是一种常用的页面置换算法
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 15:43
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V> {
    private int maxSize;

    public LRUMap(int maxSize) {
        super(maxSize, 0.75F, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return (size() > this.maxSize);
    }
}
