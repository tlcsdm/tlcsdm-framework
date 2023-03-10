package com.tlcsdm.framework.proxy.impl;

import com.tlcsdm.framework.proxy.TargetSource;

public class SingletonTargetSource implements TargetSource {
    private final Object target;
    private final Class type;

    public SingletonTargetSource(Object target) {
        this.type = target.getClass();
        this.target = target;

    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public boolean isStatic() {
        return true;
    }

    @Override
    public Class getTargetClass() {
        return type;
    }
}
