package com.tlcsdm.framework.context.listener.impl;

import com.tlcsdm.framework.context.factory.Aware;
import com.tlcsdm.framework.context.listener.ApplicationEventPublisher;

public interface ApplicationPublisherAware extends Aware {
    void setApplicationPublisher(ApplicationEventPublisher applicationPublisher);

    @Override
    default Class[] getAwareTypes() {
        return new Class[]{ApplicationEventPublisher.class};
    }

    @Override
    default void setAware(Object... awareValue) {
        setApplicationPublisher((ApplicationEventPublisher) awareValue[0]);
    }

    ;
}
