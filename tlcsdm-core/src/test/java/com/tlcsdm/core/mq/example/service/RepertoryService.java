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
import com.tlcsdm.core.mq.Subscribe;
import com.tlcsdm.core.pojo.Order;

public class RepertoryService {

    /**
     * 自动生成一个[Subscriber]对象,并关注声明的频道
     * [Subscriber]此对象根据注解声明:
     * 关注了[subChannelIDs]为[bilibili]的这一个频道
     * 并且只对该频道主题(title)为[o001]的消息(Message)感兴趣
     * 当发布者向[bilibili]频道发布主题为[o001]的消息时会自动调用该方法
     *
     * @param orderMessage 此方法返回值无意义
     */
    @Subscribe(title = "o001", subChannelIDs = {"bilibili"})
    public void inventoryReduction(Message<Order> orderMessage) {
        Order content = orderMessage.getContent();
        String commodityID = content.getCommodityID();
        Integer count = content.getCount();
        System.out.println("关注了[bilibili]频道,对[o001]主题感兴趣的订阅者");
        System.out.println("ID为[" + commodityID + "]的商品减少库存[" + count + "]个");
    }
}
