package com.lianekai.core.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *  springApplicationContext
 *  用于非spring环境下获取springContext
 *  获取spring IOC容器管理的bean对象
 *  注意需要设置扫描路径，加载bean
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/19 14:24
 */
@Component
public class LekContextHolder implements ApplicationContextAware, BeanPostProcessor {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LekContextHolder.applicationContext=applicationContext;
    }
}



/**
 * BeanPostProcessor
 * BeanPostProcessor也称为Bean后置处理器，它是Spring中定义的接口，在Spring容器的创建过程中（具体为Bean初始化前后）会回调BeanPostProcessor中定义的两个方法
 * 对应是bean初始化前的处理和bean初始化后的处理
 * */