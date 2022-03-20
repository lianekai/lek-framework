package com.lianekai.util.tree;

/**
 * 树节点信息消费接口
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 20:50
 */
public interface TreeConsumer<P,C> {
    void apply(P parent, C child);
}
