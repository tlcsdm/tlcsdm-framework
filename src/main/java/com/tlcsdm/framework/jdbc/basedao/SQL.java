package com.tlcsdm.framework.jdbc.basedao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Deprecated

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SQL {
    String value() default "";
}
