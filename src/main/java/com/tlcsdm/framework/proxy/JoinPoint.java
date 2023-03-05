package com.tlcsdm.framework.proxy;

public interface JoinPoint {

    Object proceed() throws Throwable;

    Object getThis();
}
