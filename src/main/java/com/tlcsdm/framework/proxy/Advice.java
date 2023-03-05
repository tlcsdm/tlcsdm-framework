package com.tlcsdm.framework.proxy;

import com.tlcsdm.framework.proxy.impl.JdkMethodInvocation;

public interface Advice {
    Object invoke(JdkMethodInvocation invocation) throws Throwable;
}
