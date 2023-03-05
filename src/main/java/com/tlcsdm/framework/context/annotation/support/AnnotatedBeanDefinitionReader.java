package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Configuration;
import com.tlcsdm.framework.bean.support.AnnotationBeanDefinitionBuilder;
import com.tlcsdm.framework.context.condition.impl.AnnotationConditionMatcher;
import com.tlcsdm.framework.context.exception.BeanDefinitionStoreException;
import com.tlcsdm.framework.context.factory.ApplicationContext;
import com.tlcsdm.framework.core.util.AnnotationUtils;

import java.beans.Introspector;

public class AnnotatedBeanDefinitionReader {
    private final ApplicationContext context;
    private final AnnotationConditionMatcher matcher;

    public AnnotatedBeanDefinitionReader(ApplicationContext context, AnnotationConditionMatcher matcher) {
        this.context = context;
        this.matcher = matcher;
    }

    public void register(Class<?>... componentClasses) {
        for (Class<?> componentClass : componentClasses) {
            registerBean(componentClass);
        }
    }

    private void registerBean(Class<?> beanClass) {
        doRegisterBean(beanClass);
    }

    private void doRegisterBean(Class<?> beanClass) {
        BeanDefinition bd = AnnotationBeanDefinitionBuilder.getBeanDefinition(beanClass);
        try {
            context.registerBeanDefinition(Introspector.decapitalize(beanClass.getSimpleName()), bd);
            if (isConfigurationClass(bd)) {
                AnnotationConfigParser.parseConfiguration(context, matcher, bd);
            }
        } catch (BeanDefinitionStoreException e) {
            throw new BeanDefinitionStoreException("beanClass : " + beanClass + " is AlreadyRegistered ", e);
        }
    }

    protected boolean isConfigurationClass(BeanDefinition beanDefinition) {
        Class beanClass = beanDefinition.getBeanClass();
        return matcher.isMeeConditions(beanClass)
                && AnnotationUtils.isAnnotationPresent(beanClass, Configuration.class);
    }

}
