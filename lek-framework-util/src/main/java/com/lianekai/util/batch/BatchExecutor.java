package com.lianekai.util.batch;

import java.util.List;
import java.util.function.Consumer;

/**
 * 批量执行器
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/12 16:40
 */
public class BatchExecutor {
    /**每次提交的数量*/
    private static final int DEFAULT_COUNT = 200;

    /**
     *  分批执行（默认200条）
     *
     * @param list 要分批执行的列表
     * @param consumer 执行方法
     */
    public static <T> void execute(List<T> list, Consumer<List<T>> consumer) {
        execute(list, DEFAULT_COUNT, consumer);
    }

    /**
     *  分批执行
     *
     * @param list 要分批执行的列表
     * @param batch 每批执行的数量
     * @param consumer 执行方法
     */
    public static <T> void execute(List<T> list, int batch, Consumer<List<T>> consumer) {
        if (batch <= 0) {
            batch = DEFAULT_COUNT;
        }
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = list.size();
        int times = size / batch;
        for (int i = 0; i < times; i++) {
            List<T> subList = list.subList(i * batch, (i + 1) * batch);
            consumer.accept(subList);
        }
        if (size % batch != 0) {
            List<T> subList = list.subList(times * batch, size);
            consumer.accept(subList);
        }
    }
}
