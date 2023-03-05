package com.tlcsdm.framework.bean.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Destroy;
import com.tlcsdm.framework.bean.annotation.Lazy;
import com.tlcsdm.framework.bean.annotation.Scope;
import com.tlcsdm.framework.context.annotation.support.AnnotationBeanDefinition;
import com.tlcsdm.framework.context.annotation.support.AutowiredPropertyResolver;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.core.util.AnnotationUtils;
import com.tlcsdm.framework.core.util.ReflectUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AnnotationBeanDefinitionBuilder {
    public static BeanDefinition getBeanDefinition(Class beanClass, Supplier instanceSupplier) {
        BeanDefinition beanDefinition = new AnnotationBeanDefinition();
        setScope(beanDefinition, beanClass);
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setInstanceSupplier(instanceSupplier);
        beanDefinition.setLazyInit(beanClass.isAnnotationPresent(Lazy.class));
        new AutowiredPropertyResolver(beanDefinition).parse();
        parseLifeMethod(beanDefinition, beanClass);

        return beanDefinition;
    }

    public static BeanDefinition getEmptyBeanDefinition(Class beanClass) {
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(beanClass);
        beanDefinition.setScope(Scope.SINGLETON);

        return beanDefinition;
    }

    public static BeanDefinition getBeanDefinition(Method beanMethod, Supplier instanceSupplier) {
        BeanDefinition beanDefinition = new MethodBeanDefinition(beanMethod);
        setScope(beanDefinition, beanMethod);
        beanDefinition.setBeanClass(beanMethod.getReturnType());
        beanDefinition.setLazyInit(beanMethod.isAnnotationPresent(Lazy.class));
        beanDefinition.setScope(beanMethod.isAnnotationPresent(Scope.class) ? beanMethod.getAnnotation(Scope.class).value() : Scope.SINGLETON);
        beanDefinition.setInstanceSupplier(instanceSupplier);
        new AutowiredPropertyResolver(beanDefinition).parse();
        if (beanMethod.getParameterCount() > 0) {
            Parameter[] parameters = beanMethod.getParameters();
            beanDefinition.setArguments(parameters);
        }
        parseLifeMethod(beanDefinition, beanDefinition.getBeanClass());
        return beanDefinition;
    }

    private static void setScope(BeanDefinition beanDefinition, AnnotatedElement element) {
        String scope = AnnotationUtils.getValue(element, Scope.class, Scope.SINGLETON);
        if (scope.equalsIgnoreCase(Scope.SINGLETON)) {
            beanDefinition.setScope(Scope.SINGLETON);
        } else if (scope.equalsIgnoreCase(Scope.PROTOTYPE)) {
            beanDefinition.setScope(Scope.PROTOTYPE);
        } else {
            throw new BeansException();
        }
    }

    private static void parseLifeMethod(BeanDefinition beanDefinition, Class beanClass) {
        AtomicReference<String> destroy = new AtomicReference<>();
//        AtomicReference<String> postConstruct = new AtomicReference<>();
        ReflectUtils.forEachMethod(beanClass, method -> {
            if (AnnotationUtils.isAnnotationPresent(method, Destroy.class)) {
                destroy.set(method.getName());
                return true;
            }
//            else if (AnnotationUtils.isAnnotationPresent(method, PostConstruct.class)){
//                postConstruct.set(method.getName());
//                return true;
//            }
            return false;
        });

        String destroyMethodName = destroy.get();
//        String initMethodName = postConstruct.get();
        if (destroyMethodName != null) {
            beanDefinition.setDestroyMethodName(destroyMethodName);
        }
//        if (initMethodName != null) {
//            beanDefinition.setInitMethodName(initMethodName);
//        }
    }

    public static BeanDefinition getBeanDefinition(Method beanMethod) {
        return getBeanDefinition(beanMethod, null);
    }

    public static BeanDefinition getBeanDefinition(Class beanClass) {
        return getBeanDefinition(beanClass, null);
    }
}
