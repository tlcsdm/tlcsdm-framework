package com.tlcsdm.framework.context.annotation;

import com.tlcsdm.framework.bean.BeanDefinition;

public interface PropertySetter {
    void setBeanProperty(BeanDefinition beanDefinition, Object bean);
}
