package com.tlcsdm.framework.proxy.annotation;

import com.tlcsdm.framework.bean.annotation.Import;
import com.tlcsdm.framework.proxy.impl.ProxyBeanPostProcessor;
import com.tlcsdm.framework.proxy.impl.ProxyScannerPostProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({ProxyScannerPostProcessor.class, ProxyBeanPostProcessor.class})
public @interface EnableProxy {
    String[] path() default {};
}
