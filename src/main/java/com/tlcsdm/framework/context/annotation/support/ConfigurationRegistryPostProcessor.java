package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Configuration;
import com.tlcsdm.framework.context.condition.ConditionMatcher;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.ApplicationContext;
import com.tlcsdm.framework.context.factory.Aware;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistry;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistryPostProcessor;
import com.tlcsdm.framework.core.util.AnnotationUtils;

public class ConfigurationRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Aware {

    private ConditionMatcher classConditionMatcher;

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
            if (isConfigurationClass(beanDefinition)) {
                AnnotationConfigParser.parseConfiguration(applicationContext, classConditionMatcher, beanDefinition);
            }
        }
    }

    protected boolean isConfigurationClass(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        return classConditionMatcher.isMeeConditions(beanClass)
                && AnnotationUtils.isAnnotationPresent(beanClass, Configuration.class);
    }

    @Override
    public void setAware(Object... awareValue) {
        classConditionMatcher = (ConditionMatcher) awareValue[0];
        applicationContext = (ApplicationContext) awareValue[1];
    }

    @Override
    public Class[] getAwareTypes() {
        return new Class[]{
                ConditionMatcher.class,
                ApplicationContext.class
        };
    }

}
