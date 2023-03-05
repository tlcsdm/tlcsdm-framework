package com.tlcsdm.framework.context_rpc;

import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@Autowired
public @interface Reference {
    @AliasFor(annotation = Autowired.class)
    String value() default "";

    String version() default "";
}
