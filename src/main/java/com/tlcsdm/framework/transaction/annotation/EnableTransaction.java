package com.tlcsdm.framework.transaction.annotation;

import com.tlcsdm.framework.bean.annotation.Import;
import com.tlcsdm.framework.proxy.impl.ProxyBeanPostProcessor;
import com.tlcsdm.framework.transaction.TransactionManagerRegistryPostProcessor;
import com.tlcsdm.framework.transaction.support.TransactionManagerBeanPostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({TransactionManagerBeanPostProcessor.class, TransactionManagerRegistryPostProcessor.class, ProxyBeanPostProcessor.class})
public @interface EnableTransaction {
    boolean autoRegistryManager() default true;
}
