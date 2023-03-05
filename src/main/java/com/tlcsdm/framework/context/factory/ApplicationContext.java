package com.tlcsdm.framework.context.factory;

import com.tlcsdm.framework.context.listener.ApplicationEventPublisher;

public interface ApplicationContext extends ApplicationEventPublisher, BeanDefinitionRegistry, BeanFactory, ConfigurableListableBeanFactory {
    default String getApplicationContextName() {
        return this.getClass().getName();
    }

    void register(Class... beanClass);

}
