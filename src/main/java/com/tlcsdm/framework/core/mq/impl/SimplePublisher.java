package com.tlcsdm.framework.core.mq.impl;

import com.tlcsdm.framework.core.mq.Channel;
import com.tlcsdm.framework.core.mq.Message;
import com.tlcsdm.framework.core.mq.Publisher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SimplePublisher<T> implements Publisher<T> {

    Set<Channel> channels = new HashSet<>();

    @Override
    public void publish(Message<T> message) {
        for (Channel channel : channels) {
            channel.push(message);
        }
    }

    @Override
    public Publisher<T> register(Channel... channels) {
        this.channels.addAll(Arrays.asList(channels));
        return this;
    }
}
