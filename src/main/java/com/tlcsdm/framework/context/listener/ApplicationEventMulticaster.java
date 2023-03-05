package com.tlcsdm.framework.context.listener;

public interface ApplicationEventMulticaster {
    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void removeAllListeners();

    void multicastEvent(ApplicationEvent event);

}
