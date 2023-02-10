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

package com.tlcsdm.core.mq;

import com.tlcsdm.core.mq.example.service.OrderService;
import com.tlcsdm.core.mq.example.service.RepertoryService;
import com.tlcsdm.core.mq.example.service.UserService;
import com.tlcsdm.core.pojo.Order;
import com.tlcsdm.core.pojo.Student;

public class MessageTest {
    public static void main(String[] args) {
//        simp();
        annotationExample();
    }

    private static void annotationExample() {
        AnnotationParser parser = new AnnotationParser();
        Channel blibili = new Channel("bilibili");
        //产生一个[OrderService.class]类型的代理对象
        //此代理对象对方法上有[Publish]注解的方法进行了aop,后置增强
        //会将方法的返回值包装为[Message]对象,并根据[Publish]注解发送到对应频道
        OrderService orderService = parser.proxyPubMethod(OrderService.class);
        //将传入对象打了[Publish]注解的属性按照注解声明自动生成发布者,并赋值给传入对象的对应属性;
        parser.injectPub(orderService);
        //根据传入的Class数组
        // 将打了[Subscribe]注解的方法按照注解的声明自动生成订阅者并关注声明的频道与感兴趣主题
        parser.scanSubs(RepertoryService.class, UserService.class);
        Order order = new Order()
                .setOrderID("o001")
                .setCommodityID("c001")
                .setCount(2)
                .setTotalPrices(9.99)
                .setUserName("zs");
//        orderService.place(order);
        orderService.placeWithAll(order);
    }

    //不用注解,最原始的方式调用
    private static void simp() {
        //        Publisher<String> simplePublisher = new SimplePublisher();
        Channel blibili = new Channel("bilibili");
        Channel nicocnico = new Channel("nicocnico");

//        Subscriber<Integer> subscriber1 = new TitleSubscriber<>("hello");
//        subscriber1.subscribe(m-> System.out.println(m.getContent()));
//        Subscriber<Integer> subscriber2 = new TitleSubscriber<>("hello");
//        subscriber2.subscribe(m-> System.out.println(m.getContent()));
//        blibili.addSubscriber(subscriber1);
//        nicocnico.addSubscriber(subscriber2);
//        simplePublisher.publish(Message.of("hello", "word"));
        nicocnico.subFrom(Subscriber.<Student>byTitle("hello",
                (m) -> System.out.println(m.getContent().getClassName())));
        blibili.subFrom(
                Subscriber.<Student>byTitle("hello",
                        (m) -> System.out.println(m.getContent())));
        Publisher.DEFAULT_PUBLISHER
                .register(nicocnico, blibili)
                .publish(Message.of("hello", new Student()));
    }
}
