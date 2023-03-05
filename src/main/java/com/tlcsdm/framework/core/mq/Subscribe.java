package com.tlcsdm.framework.core.mq;

import com.tlcsdm.framework.core.mq.impl.AbsSubMethodAdapter;
import com.tlcsdm.framework.core.mq.impl.TitleSubMethodAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Subscribe {
    String title();

    Class<? extends AbsSubMethodAdapter> adapterClass() default TitleSubMethodAdapter.class;

    String[] subChannelIDs() default {"DEFAULT"};
}
