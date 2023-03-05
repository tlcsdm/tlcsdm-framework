package com.tlcsdm.framework.proxy.impl;

import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.BeanPostProcessor;
import com.tlcsdm.framework.proxy.AdvisorRegister;

public class ProxyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (AdvisorRegister.classFilter(beanClass)) {
            if (beanClass.getInterfaces().length > 0) {
                return new JdkProxyFactory(bean).getProxy();
            }
            return new ClassProxyFactory(bean).getProxy();
        }
        return bean;
    }
}
