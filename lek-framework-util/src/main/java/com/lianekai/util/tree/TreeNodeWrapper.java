package com.lianekai.util.tree;

import com.lianekai.util.bean.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * 树节点封装类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/03/19 14:21
 */
@Slf4j
public class TreeNodeWrapper {

    private Class<?> clazz;
    private Field nodeCode;
    private Field parentNodeCode;
    private Field nodeChild;

    public TreeNodeWrapper(Class<?> clazz) {
        this.clazz = clazz;
        nodeCode = BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeCode.class);
        parentNodeCode = BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeParentCode.class);
        nodeChild = BeanUtils.getOneFieldByAnnotation(clazz, TreeNodeChild.class);

        setFieldAccessible(nodeCode);
        setFieldAccessible(parentNodeCode);
        setFieldAccessible(nodeChild);
    }
    private void setFieldAccessible(Field field)
    {
        if(field!=null)
        {
            field.setAccessible(true);
        }
    }

    public String getNodeCode(Object object) {
        Object value = getFieldValue(object, nodeCode);
        return value == null ? "" : value.toString();
    }

    public void setNodeCode(Object object, Object value) {
        setFiledValue(object, nodeCode, value);
    }

    public String getParentNodeCode(Object object) {
        Object value = getFieldValue(object, parentNodeCode);
        return value == null ? "" : value.toString();
    }

    public void setParentNodeCode(Object object, Object value) {
        setFiledValue(object, parentNodeCode, value);
    }

    public Collection getNodeChild(Object object) {
        return (Collection) getFieldValue(object, nodeChild);
    }

    public void setChildNodes(Object object, Object value) {
        setFiledValue(object, nodeChild, value);
    }


    public Object getFieldValue(Object object, Field field) {

        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
        return result;
    }

    public void setFiledValue(Object object, Field field, Object value) {

        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            log.error("", e);
        }
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
