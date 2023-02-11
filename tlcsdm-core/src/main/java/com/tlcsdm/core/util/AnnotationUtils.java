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

package com.tlcsdm.core.util;

import com.tlcsdm.core.support.AnnotationMetadata;
import com.tlcsdm.core.support.impl.AnnotationConvertHandler;
import com.tlcsdm.core.support.impl.StandardAnnotationMetadata;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class AnnotationUtils {
    private AnnotationUtils() {
    }

    public static boolean isAnnotationPresent(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        if (element.isAnnotationPresent(annotationType)) {
            return true;
        }
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (annotationFilter(type)) {
                continue;
            }
            if (AnnotationUtils.isAnnotationPresent(type, annotationType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获得该元素上的的传入注解,如果不存在返回null
     *
     * @param element        元素
     * @param annotationType 需要的注解类型
     * @param <T>            类型需继承Annotation类
     * @return 需要的注解实例
     */
    public static <T extends Annotation> T getAnnotation(AnnotatedElement element, Class<T> annotationType) {
        return getAnnotation(element, element, annotationType);
    }

    private static <R extends Annotation> R getAnnotation(AnnotatedElement element, AnnotatedElement oldElement, Class<R> annotationType) {
        if (element.isAnnotationPresent(annotationType)) {
            return doGetAnnotation(element, oldElement, annotationType);
        }
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (annotationFilter(type)) {
                continue;
            }
            if (AnnotationUtils.isAnnotationPresent(type, annotationType)) {
                return AnnotationUtils.getAnnotation(type, element, annotationType);
            }
        }
        return null;
    }

    /**
     * 递归过滤条件
     */
    private static boolean annotationFilter(Class<? extends Annotation> type) {
        return type.equals(Documented.class) || type.equals(Target.class) || type.equals(Retention.class);
    }

    public static <R extends Annotation> List<R> getAnnotations(AnnotatedElement element, Class<R> annotationType) {
        return getAnnotations(element, element, annotationType, new ArrayList<>());
    }

    protected static <R extends Annotation> List<R> getAnnotations(AnnotatedElement element, AnnotatedElement oldElement,
                                                                   Class<R> annotationType, List<R> list) {
        if (element.isAnnotationPresent(annotationType)) {
            list.add(doGetAnnotation(element, oldElement, annotationType));
        }
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (annotationFilter(type)) {
                continue;
            }
            if (AnnotationUtils.isAnnotationPresent(type, annotationType)) {
                AnnotationUtils.getAnnotations(type, element, annotationType, list);
            }
        }
        return list;
    }

    private static <R extends Annotation> R doGetAnnotation(AnnotatedElement element, AnnotatedElement oldElement,
                                                            Class<R> annotationType) {
        return element.equals(oldElement) ?
                element.getAnnotation(annotationType) :
                (R) Proxy.newProxyInstance(AnnotationUtils.class.getClassLoader(), new Class[]{annotationType},
                        new AnnotationConvertHandler<>(element, oldElement, annotationType));
    }

    public static <R extends Annotation> AnnotationMetadata<R> getAnnotationMetadata(AnnotatedElement element, Class<R> annotationType) {
        return getAnnotationMetadata(element, element, annotationType);
    }

    private static <R extends Annotation> AnnotationMetadata<R> getAnnotationMetadata(AnnotatedElement element, AnnotatedElement oldElement, Class<R> annotationType) {
        if (element.isAnnotationPresent(annotationType)) {
            R meta = doGetAnnotation(element, oldElement, annotationType);
            StandardAnnotationMetadata<R> standardAnnotationMetadata = new StandardAnnotationMetadata<>(meta, annotationType, oldElement);
            if (!element.equals(oldElement)) {
                Class<Annotation> srcAnnotationType = ((Class) element).asSubclass(Annotation.class);
                standardAnnotationMetadata.setSrcAnnotation(AnnotationUtils.getAnnotation(oldElement, srcAnnotationType));
            }
            return standardAnnotationMetadata;
        }
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (annotationFilter(type)) {
                continue;
            }
            if (AnnotationUtils.isAnnotationPresent(type, annotationType)) {
                return AnnotationUtils.getAnnotationMetadata(type, element, annotationType);
            }
        }
        return null;
    }

    public static <R extends Annotation> List<AnnotationMetadata<R>> getAnnotationMetadatas(AnnotatedElement element, Class<R> annotationType) {
        return getAnnotationMetadatas(element, element, annotationType, new ArrayList<>());
    }

    private static <R extends Annotation> List<AnnotationMetadata<R>> getAnnotationMetadatas(AnnotatedElement element, AnnotatedElement oldElement,
                                                                                             Class<R> annotationType, List<AnnotationMetadata<R>> list) {
        if (element.isAnnotationPresent(annotationType)) {
            R meta = doGetAnnotation(element, oldElement, annotationType);
            StandardAnnotationMetadata<R> standardAnnotationMetadata = new StandardAnnotationMetadata<>(meta, annotationType, oldElement);
            if (!element.equals(oldElement)) {
                Class<Annotation> srcAnnotationType = ((Class) element).asSubclass(Annotation.class);
                standardAnnotationMetadata.setSrcAnnotation(getAnnotation(oldElement, srcAnnotationType));
            }
            list.add(standardAnnotationMetadata);
        }
        Annotation[] annotations = element.getAnnotations();
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if (annotationFilter(type)) {
                continue;
            }
            if (isAnnotationPresent(type, annotationType)) {
                getAnnotationMetadatas(type, element, annotationType, list);
            }
        }
        return list;
    }

    public static <T extends Annotation> void getAnnotationAndHandle(Class<T> type, Method[] methods, Consumer<T> handler) {
        for (Method method : methods) {
            T annotation = getAnnotation(method, type);
            if (annotation == null) {
                continue;
            }
            handler.accept(annotation);
        }
    }

    public static <T> T getValue(AnnotatedElement element, Class<? extends Annotation> annotationType, T defaultValue) {
        return getValue(element, annotationType, "value", defaultValue);
    }

    public static <T> T getValue(AnnotatedElement element, Class<? extends Annotation> annotationType, String methodName, T defaultValue) {
        Annotation annotation = getAnnotation(element, annotationType);
        if (annotation == null) {
            return defaultValue;
        }
        try {
            Method annotationMethod = annotationType.getMethod(methodName);
            T value = (T) ReflectUtils.invokeMethod(annotationMethod, annotation);
            if (value instanceof String && value.equals("")) {
                return defaultValue;
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
