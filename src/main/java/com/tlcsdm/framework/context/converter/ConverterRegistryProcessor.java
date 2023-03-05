package com.tlcsdm.framework.context.converter;

import com.tlcsdm.framework.context.factory.BeanFactoryPostProcessor;
import com.tlcsdm.framework.context.factory.ConfigurableListableBeanFactory;
import com.tlcsdm.framework.core.annotation.Order;

@Order(1)
public class ConverterRegistryProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    }

}
