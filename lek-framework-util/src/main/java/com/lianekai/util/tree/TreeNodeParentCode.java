package com.lianekai.util.tree;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 20:48
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeNodeParentCode {
    String[] groups() default {};
}
