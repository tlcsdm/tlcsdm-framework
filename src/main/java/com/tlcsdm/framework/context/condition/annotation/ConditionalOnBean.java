package com.tlcsdm.framework.context.condition.annotation;

import com.tlcsdm.framework.context.condition.impl.ConditionOnBean;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionOnBean.class)
public @interface ConditionalOnBean {
    boolean IS_MISS = false;

    String[] beanNames() default {};

    String[] beanTypes() default {};

    Class[] beanClasses() default {};
}
