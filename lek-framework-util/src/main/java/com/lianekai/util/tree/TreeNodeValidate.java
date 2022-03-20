package com.lianekai.util.tree;

import com.lianekai.util.bean.BeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TreeNodeValidate
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/20 20:49
 */
public class TreeNodeValidate {
    private static final Map<Class,Boolean> validateMap=new ConcurrentHashMap<>();

    public static <T> boolean check(Class<T> clazz) {

        Boolean validateCache= validateMap.get(clazz);
        if(validateCache!=null)
        {
            return validateCache.booleanValue();
        }

        if (BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeCode.class) == null
                || BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeParentCode.class) == null
                || BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeChild.class) == null
        ) {
            validateMap.put(clazz,Boolean.FALSE);
            return false;
        }
        validateMap.put(clazz,Boolean.TRUE);
        return true;
    }
}
