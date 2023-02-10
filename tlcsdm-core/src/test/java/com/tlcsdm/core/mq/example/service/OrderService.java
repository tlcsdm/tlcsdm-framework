/*
 * Copyright (c) 2019, 2023 unknowIfGuestInDream
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of unknowIfGuestInDream, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UNKNOWIFGUESTINDREAM BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.tlcsdm.core.mq.example.service;

import com.tlcsdm.core.mq.Message;
import com.tlcsdm.core.mq.Publish;
import com.tlcsdm.core.mq.Publisher;
import com.tlcsdm.core.pojo.Order;

public class OrderService {

    @Publish
    private Publisher<Order> defaultPublisher;
    //根据
    @Publish(subChannelIDs = {"bilibili"})
    private Publisher<Order> bilibiliPublisher;

    /**
     * 测试通过反射注入的Publisher属性
     *
     * @param order
     */
    public void place(Order order) {
        //给[DEFAULT]频道发送主题(Title)为[order.getOrderID()],内容(Content)为order对象的消息(Message)对象
        defaultPublisher.publish(Message.of(order.getOrderID(), order));
        //给[bilibili]频道发送主题(Title)为[order.getOrderID()],内容(Content)为order对象的消息(Message)对象
//        bilibiliPublisher.publish(Message.of(order.getOrderID(),order));
    }

    /**
     * 此处使用了代理对象进行了aop
     * 调用此方法后关注了subChannelID为[bilibili]的频道
     * 并对messageTitle为[o001]感兴趣的订阅者
     * 会收到返回值作为Content的Message对象
     *
     * @param order
     * @return 返回值将会作为Message对象的Content属性
     */
    @Publish(messageTitle = "o001", subChannelIDs = {"bilibili"})
    public Order placeWithBilibili(Order order) {
        return order;
    }

    /**
     * 此处使用了代理对象进行了aop
     * 调用此方法后关注了subChannelID为[bilibili]和[DEFAULT(注解默认值)]的频道
     * 并对messageTitle为[o001]感兴趣的订阅者
     * 会收到返回值作为Content的Message对象
     *
     * @param order
     * @return 返回值将会作为Message对象的Content属性
     */
    @Publish(messageTitle = "o001", subChannelIDs = {"bilibili", "DEFAULT"})
    public Order placeWithAll(Order order) {
        return order;
    }

}
