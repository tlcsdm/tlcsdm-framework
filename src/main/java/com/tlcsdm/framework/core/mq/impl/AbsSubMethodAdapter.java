package com.tlcsdm.framework.core.mq.impl;

import com.tlcsdm.framework.core.mq.Message;
import com.tlcsdm.framework.core.mq.Subscriber;
import com.tlcsdm.framework.core.util.ReflectUtils;

import java.lang.reflect.Method;

public abstract class AbsSubMethodAdapter<T> implements Subscriber<T> {

    private final Method acceptMethod;
    private final Object instance;

    private Class<T> msgType;

    public AbsSubMethodAdapter(Method method, Object instance) {
        this.acceptMethod = method;
        this.instance = instance;
    }

    @Override
    public void accept(Message message) {
        ReflectUtils.invokeMethod(acceptMethod, instance, message);
    }
}
