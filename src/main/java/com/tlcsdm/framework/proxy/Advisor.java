package com.tlcsdm.framework.proxy;

public interface Advisor {
    default boolean classFilter(Class<?> targetClass) {
        return getPointcut().classFilter(targetClass);
    }

    ;

    Advice getAdvice();

    Pointcut getPointcut();
}
