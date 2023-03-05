package com.tlcsdm.framework.context.condition;

import com.tlcsdm.framework.core.support.AnnotationMetadata;

import java.lang.reflect.AnnotatedElement;

@FunctionalInterface
public interface Condition {
    boolean matches(AnnotationMetadata metadata, AnnotatedElement element);
}
