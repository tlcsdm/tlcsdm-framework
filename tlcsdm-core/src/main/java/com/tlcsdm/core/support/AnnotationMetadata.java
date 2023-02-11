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

package com.tlcsdm.core.support;

import com.tlcsdm.core.exception.ReflectException;
import com.tlcsdm.core.util.AnnotationUtils;
import com.tlcsdm.core.util.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public interface AnnotationMetadata<T extends Annotation> {
    T getAnnotation();

    Annotation getSrcAnnotation();

    Class<T> getAnnotationType();

    AnnotatedElement getSrcAnnotationElement();

    default String getSrcAnnotationElementClassName() {
        return ((Class) getSrcAnnotationElement()).getName();
    }

    ;

    default Class getSrcAnnotationElementClass() {
        return ((Class) getSrcAnnotationElement());
    }

    ;

    default boolean hasAnnotation(Class<? extends Annotation> annotationType) {
        return AnnotationUtils.isAnnotationPresent(this.getSrcAnnotationElement(), annotationType);
    }

    default <R extends Annotation> List<R> getAnnotationsInSrc(Class<R> annotationType) {
        return AnnotationUtils.getAnnotations(this.getSrcAnnotationElement(), annotationType);
    }

    default <R extends Annotation> R getAnnotationInSrc(Class<R> annotationType) {
        return AnnotationUtils.getAnnotation(this.getSrcAnnotationElement(), annotationType);
    }

    default <T> T getAnnotationValue(String attributeName) {
        return getValue(this.getAnnotation(), attributeName);
    }

    default <T> T getSrcAnnotationValue(String attributeName) {
        return getValue(this.getSrcAnnotation(), attributeName);
    }

    default <T extends Object> T getValue(Annotation annotation, String attributeName) {
        Objects.requireNonNull(annotation);
        Class<? extends Annotation> type = annotation.annotationType();
        try {
            Method method = ReflectUtils.getMethod(type, attributeName);
            return (T) ReflectUtils.invokeMethod(method, annotation);
        } catch (ReflectException e) {
            Field field = ReflectUtils.getField(type, attributeName);
            return (T) ReflectUtils.getFieldValue(field, null);
        }
    }
}
