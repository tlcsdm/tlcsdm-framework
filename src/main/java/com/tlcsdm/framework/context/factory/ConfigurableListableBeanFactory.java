package com.tlcsdm.framework.context.factory;

import com.tlcsdm.framework.bean.BeanDefinition;

public interface ConfigurableListableBeanFactory {
    void registerProcessor(String beanName, BeanDefinition beanDefinition);

    BeanDefinition registerProcessor(Class configClass);

//    List<BeanFactoryPostProcessor> getBeanFactoryPostProcessors();
//
//    Set<BeanInstanceProcessor> getBeanInstanceProcessors();
//
//    Set<BeanPostProcessor> getBeanPostProcessors();

}
