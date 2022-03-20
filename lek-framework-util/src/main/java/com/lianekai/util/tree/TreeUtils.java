package com.lianekai.util.tree;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 树结构生成工具类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 14:19
 */
@Slf4j
public class TreeUtils {
    private TreeUtils() {
    }

    private static final Map<Class, TreeNodeWrapper> WRAPPER_CACHE = new ConcurrentHashMap<>();

}
