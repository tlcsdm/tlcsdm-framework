package com.tlcsdm.framework.jdbc.mapper_support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.support.AnnotationBeanDefinitionBuilder;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistry;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistryPostProcessor;

public class MapperSupportImport implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        BeanDefinition factory = AnnotationBeanDefinitionBuilder.getBeanDefinition(MapperSupportFactory.class);
        BeanDefinition factoryBean = AnnotationBeanDefinitionBuilder.getBeanDefinition(MapperSupportFactoryBean.class);
        registry.registerBeanDefinition(MapperSupportFactory.class.getSimpleName(), factory);
        registry.registerBeanDefinition(MapperSupportFactoryBean.class.getSimpleName(), factoryBean);
    }
}
