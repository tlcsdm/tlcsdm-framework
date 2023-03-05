package com.tlcsdm.framework.context.listener;

public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);

}