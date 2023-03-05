package com.tlcsdm.framework.context.listener.impl;

import com.tlcsdm.framework.context.factory.ApplicationContext;
import com.tlcsdm.framework.context.listener.ApplicationEvent;

public class ApplicationContextEvent extends ApplicationEvent<ApplicationContext> {
    private ApplicationContext applicationContext;

    public ApplicationContextEvent(ApplicationContext source) {
        super(source);
        applicationContext = source;
    }

    public ApplicationContext getApplicationContext() {
        return (ApplicationContext) this.getSource();
    }
}
