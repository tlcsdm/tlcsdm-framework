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

package com.tlcsdm.core.support.impl;

import com.tlcsdm.core.annotation.AliasFor;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationConvertHandler<T extends Annotation> implements InvocationHandler {
    //映射map，key: 代理注解的方法名 ,val: 代理对象的方法名所对应的目标注解上的方法
    private Map<String, Method> annotationMethodMapper;
    //目标注解类型
    private Class targetType;
    //代理注解类型
    private Class proxyType;
    //目标注解类上的代理对象类型实例
    private T targetTypeAnnotation;
    //注释元素上的目标注解类型实例
    private T targetAnnotation;

    public AnnotationConvertHandler(AnnotatedElement targetType, AnnotatedElement targetElement, Class<T> proxyType) {
        this.annotationMethodMapper = new HashMap<>();
        this.targetType = ((Class) targetType).asSubclass(Annotation.class);
        this.proxyType = proxyType;
        //获得targetType(Service.class)上的proxyType(Component.class)代理对象类型实例
        this.targetTypeAnnotation = (T) this.targetType.getAnnotation(proxyType);
        //获得注释元素(Test.class)上的目标注解类型(Service.class)实例
        this.targetAnnotation = (T) targetElement.getAnnotation(this.targetType);
        InitializationMapper();
    }

    private void InitializationMapper() {
        Method[] targetMethods = targetType.getDeclaredMethods();
        for (Method targetMethod : targetMethods) {
            AliasFor aliasFor = targetMethod.getAnnotation(AliasFor.class);
            if (aliasFor != null && aliasFor.annotation().isAssignableFrom(proxyType)) {
                //Component
                String proxyMethodName = aliasFor.attribute().equals("") ? targetMethod.getName() : aliasFor.attribute();
                annotationMethodMapper.put(proxyMethodName, targetMethod);
            }
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //目标设置为目标注解类(Service.class)上的代理对象类型(Component.class)实例
        Object target = targetTypeAnnotation;
        //获取当前正在执行的代理注解的方法名
        String name = method.getName();
        if (name.equals("toString")) {
            return targetAnnotation.toString();
        }
        //如果不为空
        if (annotationMethodMapper.containsKey(name)) {
            //将目标转换为注释元素(Test.class)上的目标注解类型实例(Service.class)
            target = targetAnnotation;
            //将执行的方法转换为目标注解类型(Service.class)的方法,此方法是(Service.class)的方法
            //method对象原本是代理注解上的方法对象
            method = annotationMethodMapper.get(name);
        }

        return method.invoke(target);
    }
}
