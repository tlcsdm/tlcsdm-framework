package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistry;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistryPostProcessor;
import com.tlcsdm.framework.context.factory.ConfigurableListableBeanFactory;
import com.tlcsdm.framework.core.support.AnnotationMetadata;

public abstract class BeanDefinitionImportRegistry implements BeanDefinitionRegistryPostProcessor {
    @Autowired
    private AnnotationMetadata annotationMetadata;

    @Override
    public final void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        postProcessBeanFactory(annotationMetadata, beanFactory);
    }

    @Override
    public final void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        postProcessBeanDefinitionRegistry(annotationMetadata, registry);
    }

    public void postProcessBeanFactory(AnnotationMetadata annotationMetadata, ConfigurableListableBeanFactory beanFactory) {

    }

    public void postProcessBeanDefinitionRegistry(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) throws BeansException {

    }

}
