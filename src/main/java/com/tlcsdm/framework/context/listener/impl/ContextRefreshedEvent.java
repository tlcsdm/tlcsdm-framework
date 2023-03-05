package com.tlcsdm.framework.context.listener.impl;

import com.tlcsdm.framework.context.factory.ApplicationContext;

public class ContextRefreshedEvent extends ApplicationContextEvent {
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }

}