package com.tlcsdm.framework.context.listener.annotation;

import com.tlcsdm.framework.context.listener.ApplicationEvent;
import com.tlcsdm.framework.context.listener.impl.ApplicationListenerMethodAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Listener {
    Class<? extends ApplicationListenerMethodAdapter> listenerType() default ApplicationListenerMethodAdapter.class;

    Class<? extends ApplicationEvent> eventType() default ApplicationEvent.class;

    String[] multicasterNames() default {};
}
