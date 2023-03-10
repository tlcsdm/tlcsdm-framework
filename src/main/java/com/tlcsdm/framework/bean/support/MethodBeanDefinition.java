package com.tlcsdm.framework.bean.support;

import com.tlcsdm.framework.context.annotation.support.AnnotationBeanDefinition;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodBeanDefinition extends AnnotationBeanDefinition {

    private Object configBean;
    private Method instanceMethod;

    public MethodBeanDefinition(Method instanceMethod) {
        this.instanceMethod = instanceMethod;
    }

    @Override
    public Object doInstance(Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (configBean == null) {
            configBean = instanceSupplier.get();
        }
        return instanceMethod.invoke(configBean, args);
    }
}
