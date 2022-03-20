package com.lianekai.util.cache;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于HashMap 与读写锁的本地缓存
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 15:40
 */
@Slf4j
public class Cache<K,V> {

    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock read = rwl.readLock();
    private Lock write = rwl.writeLock();

    private Map<K, V> cacheMap = null;

    public Cache(int initialCapacity) {
        this.cacheMap = new LRUMap<>(initialCapacity);
    }

    public Cache(Map<K, V> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public V get(K key, Callable<? extends V> loader) {
        V value = null;
        read.lock();
        try {
            value = cacheMap.get(key);
            if (value == null) {
                read.unlock();
                write.lock();
                try {
                    value = cacheMap.get(key);
                    if (value == null) {
                        value = loader.call();
                        cacheMap.put(key, value);
                    }
                } catch (Exception e) {
                    log.error("Cache Callback Error", e);
                }finally {
                    write.unlock();
                    read.lock();
                }
            }
        } finally {
            read.unlock();
        }
        return value;
    }


}
