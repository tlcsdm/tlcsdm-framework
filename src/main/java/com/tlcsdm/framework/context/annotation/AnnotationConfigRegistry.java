package com.tlcsdm.framework.context.annotation;

public interface AnnotationConfigRegistry {
    void register(Class<?>... componentClasses);

    void scan(String... basePackages);
}
