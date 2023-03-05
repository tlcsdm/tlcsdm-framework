package com.tlcsdm.framework.context.factory;

import com.tlcsdm.framework.context.exception.BeansException;

public interface BeanPostProcessor extends ApplicationProcessor {
    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
