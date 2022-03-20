package com.lianekai.util.tree;

import java.lang.annotation.*;

/**
 * 树节点的子节点集合
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 20:46
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeNodeChild {
    String[] groups() default {};
}
