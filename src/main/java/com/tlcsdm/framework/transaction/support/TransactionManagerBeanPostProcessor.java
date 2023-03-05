package com.tlcsdm.framework.transaction.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.context.factory.BeanFactory;
import com.tlcsdm.framework.context.factory.BeanInstanceProcessor;
import com.tlcsdm.framework.proxy.Advisor;
import com.tlcsdm.framework.proxy.AdvisorRegister;
import com.tlcsdm.framework.transaction.TransactionManager;
import com.tlcsdm.framework.transaction.annotation.Transactional;

public class TransactionManagerBeanPostProcessor implements BeanInstanceProcessor {
    @Autowired
    private BeanFactory factory;

    @Override
    public Object instanceBefore(String beanName, BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        TransactionAdvisorBuilder builder = new TransactionAdvisorBuilder(beanClass);
        if (builder.isEnableTransaction()) {
            String managerName = null;
            if (beanClass.isAnnotationPresent(Transactional.class)) {
                managerName = beanClass.getAnnotation(Transactional.class).managerName();
            }
            final String finalManagerName = managerName;
            Advisor advisor = managerName != null ?
                    builder.buildAdvisor(() -> factory.getBean(finalManagerName, TransactionManager.class)) :
                    builder.buildAdvisor(() -> factory.getBean(TransactionManager.class));
            AdvisorRegister.registerAdvisor(advisor);
        }
        return BeanInstanceProcessor.super.instanceBefore(beanName, beanDefinition);
    }
}
