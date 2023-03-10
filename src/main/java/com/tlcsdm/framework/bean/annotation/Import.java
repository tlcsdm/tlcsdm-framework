package com.tlcsdm.framework.bean.annotation;

import com.tlcsdm.framework.context.factory.ApplicationProcessor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Import {
    Class<? extends ApplicationProcessor>[] value();
}
