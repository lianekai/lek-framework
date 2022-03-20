package com.lianekai.util.tree;

import java.lang.annotation.*;

/**
 * 树节点的nodeCode字段
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 20:47
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeNodeCode {
    String[] groups() default {};
}
