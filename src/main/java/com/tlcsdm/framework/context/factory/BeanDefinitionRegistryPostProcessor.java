package com.tlcsdm.framework.context.factory;

import com.tlcsdm.framework.context.exception.BeansException;

public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {
    default void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    ;
}
