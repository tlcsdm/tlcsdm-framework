package com.tlcsdm.framework.context.factory;

import com.tlcsdm.framework.bean.BeanDefinition;

public interface BeanInstanceProcessor extends ApplicationProcessor {

    default Object instanceBefore(String beanName, BeanDefinition beanDefinition) {
        return null;
    }

    ;

    default void instanceAfter(String beanName, BeanDefinition beanDefinition, Object bean) {
    }

    ;
}
