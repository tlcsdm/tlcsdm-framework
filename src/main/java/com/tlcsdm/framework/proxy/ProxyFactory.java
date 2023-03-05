package com.tlcsdm.framework.proxy;

public interface ProxyFactory {
    Object getProxy();

    void setTargetSource(TargetSource targetSource);

    TargetSource getTargetSource();

    void addAdvisor(Advisor advisor);
}
