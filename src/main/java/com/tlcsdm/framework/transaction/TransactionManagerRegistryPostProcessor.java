package com.tlcsdm.framework.transaction;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.support.AnnotationBeanDefinitionBuilder;
import com.tlcsdm.framework.context.annotation.support.BeanDefinitionImportRegistry;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistry;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistryPostProcessor;
import com.tlcsdm.framework.core.support.AnnotationMetadata;
import com.tlcsdm.framework.transaction.support.DataSourceTransManager;

import java.beans.Introspector;

public class TransactionManagerRegistryPostProcessor extends BeanDefinitionImportRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) throws BeansException {
        Boolean annotation = (Boolean) annotationMetadata.getSrcAnnotationValue("autoRegistryManager");
        if (!annotation) {
            return;
        }
        BeanDefinition beanDefinition = AnnotationBeanDefinitionBuilder.getBeanDefinition(DataSourceTransManager.class);
        String name = Introspector.decapitalize(DataSourceTransManager.class.getSimpleName());
        registry.registerBeanDefinition(name, beanDefinition);
    }
}
