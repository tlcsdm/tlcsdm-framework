package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.support.Property;
import com.tlcsdm.framework.context.annotation.PropertySetter;
import com.tlcsdm.framework.context.converter.ConverterRegistry;
import com.tlcsdm.framework.context.factory.BeanFactory;
import com.tlcsdm.framework.core.support.impl.LazedInvocationHandler;
import com.tlcsdm.framework.core.util.ProxyUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class DefaultPropertySetter implements PropertySetter {
    private BeanFactory factory;

    public DefaultPropertySetter(BeanFactory factory) {
        this.factory = factory;
    }

    public void setBeanProperty(BeanDefinition beanDefinition, Object bean) {
        Property[] propertys = beanDefinition.getPropertys();
        Class<?> beanClass = bean.getClass();
        for (Property property : propertys) {
            property.setBean(bean);
            if (property.isNeedAssign()) {
                Object value = getPropertyValue(property);
                property.assignPro(value);
            }
        }
    }

    protected Object getPropertyValue(Property property) {
        Object value = property.getValue();
        Class filedType = property.getType();
        //如果之前没有设置值
        if (value == null) {
            //如果该属性是懒加载的
            value = property.isLazed() ?
                    getProxyPropertyValue(property) :
                    getValue(property);
        } else if (!filedType.isAssignableFrom(property.getValueType())) {
            value = ConverterRegistry.convert(value, filedType);
        }
        property.setValue(value);
        return value;
    }

    protected <T> Object getValue(Property property) {
        Object value = property.getValue();
        Class<T> requiredType = property.getType();
        String name = property.getName();
        if (requiredType.isArray()) {
            return factory.getBeans(requiredType.getComponentType()).toArray((T[]) Array.newInstance(requiredType.getComponentType(), 0));
        } else if (List.class.isAssignableFrom(requiredType)) {
            ParameterizedType parameterizedType = (ParameterizedType) property.getGenericType();
            return factory.getBeans((Class) parameterizedType.getActualTypeArguments()[0]);
        }
        return factory.getBean(name, requiredType);
    }

    private Object getProxyPropertyValue(Property property) {
        InvocationHandler lazedInvocationHandler = new LazedInvocationHandler(() -> getValue(property));
        return ProxyUtils.creatProxyInstance(property.getType(), lazedInvocationHandler);
    }

}
