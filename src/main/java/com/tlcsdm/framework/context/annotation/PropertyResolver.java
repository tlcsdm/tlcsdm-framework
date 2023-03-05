package com.tlcsdm.framework.context.annotation;

import com.tlcsdm.framework.context.factory.Resolver;

import java.lang.reflect.AnnotatedElement;

public interface PropertyResolver extends Resolver {
     boolean needParse(AnnotatedElement element);
}
