package com.tlcsdm.framework.context.factory;

public interface BeanFactoryPostProcessor extends ApplicationProcessor {
    default void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    }

    ;
}
