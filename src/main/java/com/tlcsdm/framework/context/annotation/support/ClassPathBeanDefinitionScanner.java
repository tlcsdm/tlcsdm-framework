package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Component;
import com.tlcsdm.framework.bean.annotation.Configuration;
import com.tlcsdm.framework.context.condition.ConditionMatcher;
import com.tlcsdm.framework.context.factory.ApplicationContext;
import com.tlcsdm.framework.core.util.AnnotationUtils;

import java.beans.Introspector;

public class ClassPathBeanDefinitionScanner extends AbstractScanner {

    private ApplicationContext context;

    private ConditionMatcher conditionMatcher;

    public ClassPathBeanDefinitionScanner(ConditionMatcher conditionMatcher, ApplicationContext context) {
        this.conditionMatcher = conditionMatcher;
        this.context = context;
    }

    @Override
    public void register(Class beanClass) {
        Component component = AnnotationUtils.getAnnotation(beanClass, Component.class);
        String beanName = component.value();
        if (beanName.equals("")) {
            beanName = Introspector.decapitalize(beanClass.getSimpleName());
        }
        if (context.containsBeanDefinition(beanName)) {
            return;
        }
        BeanDefinition beanDefinition = context.registerBeanDefinition(beanName, beanClass);
        if (AnnotationUtils.isAnnotationPresent(beanClass, Configuration.class)) {
            AnnotationConfigParser.parseConfiguration(context, conditionMatcher, beanDefinition);
        }

    }

    @Override
    public boolean isRegisterClass(Class loaderClass) {

        return loaderClass != null
                && AnnotationUtils.isAnnotationPresent(loaderClass, Component.class)
                && !loaderClass.isInterface()
                && conditionMatcher.isMeeConditions(loaderClass);
    }
}



