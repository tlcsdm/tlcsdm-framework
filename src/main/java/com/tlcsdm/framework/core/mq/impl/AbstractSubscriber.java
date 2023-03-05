package com.tlcsdm.framework.core.mq.impl;

import com.tlcsdm.framework.core.mq.Message;
import com.tlcsdm.framework.core.mq.Subscriber;

import java.util.function.Consumer;

public abstract class AbstractSubscriber<T> implements Subscriber<T> {

    private Consumer<Message<T>> messageConsumer;

    public AbstractSubscriber() {
        this.messageConsumer = m -> {
        };
    }

    public AbstractSubscriber(Consumer<Message<T>> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public void accept(Message<T> message) {
        messageConsumer.accept(message);
    }
}
