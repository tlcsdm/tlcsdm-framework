package com.tlcsdm.framework.core.mq.impl;

import com.tlcsdm.framework.core.mq.Message;
import com.tlcsdm.framework.core.mq.Subscribe;

import java.lang.reflect.Method;

public class TitleSubMethodAdapter extends AbsSubMethodAdapter {

    private final String title;

    public TitleSubMethodAdapter(Method method, Object instance) {
        super(method, instance);
        Subscribe annotation = method.getAnnotation(Subscribe.class);
        this.title = annotation.title();
    }

    @Override
    public boolean isInterested(Message message) {
        return title.equals(message.getTitle());
    }

}
