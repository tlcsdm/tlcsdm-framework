package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.context.annotation.PropertyResolver;
import com.tlcsdm.framework.core.util.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;

public abstract class AbstractPropertyResolver implements PropertyResolver {

    @Override
    public boolean needParse(AnnotatedElement element) {
        return AnnotationUtils.isAnnotationPresent(element, Autowired.class);
    }
}
