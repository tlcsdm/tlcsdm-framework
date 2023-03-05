package com.tlcsdm.framework.proxy;

import com.tlcsdm.framework.context.factory.Resolver;
import com.tlcsdm.framework.proxy.annotation.After;
import com.tlcsdm.framework.proxy.annotation.Before;
import com.tlcsdm.framework.proxy.impl.AfterPoint;
import com.tlcsdm.framework.proxy.impl.BeforePoint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AspectResolve implements Resolver {
    private final Class aspectClass;
    private final static Map<Class, PointParser> POINT_MAP = new HashMap<>();

    static {
        POINT_MAP.put(After.class, new AfterPoint());
        POINT_MAP.put(Before.class, new BeforePoint());
    }

    public AspectResolve(Class aspectClass) {
        this.aspectClass = aspectClass;
    }

    @Override
    public void parse() {
        Object o = null;
        try {
            o = aspectClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Method[] methods = aspectClass.getDeclaredMethods();
        for (Method method : methods) {
            PointParser point = getPoint(method);
            if (point != null) {
                AdvisorRegister.registerAdvisor(point.getAdvisor(method, o));
            }
        }
    }

    private PointParser getPoint(Method method) {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            Class annotationType = annotation.annotationType();
            if (POINT_MAP.containsKey(annotationType)) {
                return POINT_MAP.get(annotationType);
            }
        }
        return null;
    }

    private boolean isPoint(Method method) {
        return POINT_MAP.containsKey(method);

    }

}
