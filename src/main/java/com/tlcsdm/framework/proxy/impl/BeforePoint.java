package com.tlcsdm.framework.proxy.impl;

import com.tlcsdm.framework.proxy.Advice;
import com.tlcsdm.framework.proxy.Advisor;
import com.tlcsdm.framework.proxy.PointParser;
import com.tlcsdm.framework.proxy.Pointcut;
import com.tlcsdm.framework.proxy.annotation.Before;

import java.lang.reflect.Method;

public class BeforePoint implements PointParser {
    @Override
    public Advisor getAdvisor(Method method, final Object aspectObj) {
        String pointPath = method.getAnnotation(Before.class).value();
        String methodName = pointPath.substring(pointPath.lastIndexOf('.') + 1, pointPath.lastIndexOf('('));
        String classpath = pointPath.substring(0, pointPath.lastIndexOf('.'));
        String finalMethodName = methodName;

        Advisor advisor = new Advisor() {
            @Override
            public boolean classFilter(Class<?> targetClass) {
                return getPointcut().classFilter(targetClass);
            }

            @Override
            public Advice getAdvice() {
                return invocation -> {
                    method.invoke(aspectObj);
                    return invocation.proceed();
                };
            }

            @Override
            public Pointcut getPointcut() {
                return new Pointcut() {
                    @Override
                    public boolean classFilter(Class<?> targetClass) {
                        return targetClass.getName().equals(classpath);
                    }

                    @Override
                    public boolean matches(Method method, Class<?> targetClass) {
                        return method.getName().equals(finalMethodName);
                    }
                };
            }
        };
        return advisor;
    }
}