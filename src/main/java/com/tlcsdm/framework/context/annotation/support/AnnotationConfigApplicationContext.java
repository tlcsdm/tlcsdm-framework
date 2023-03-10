package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.context.GenericApplicationContext;
import com.tlcsdm.framework.context.annotation.AnnotationConfigRegistry;
import com.tlcsdm.framework.context.condition.impl.AnnotationConditionMatcher;
import com.tlcsdm.framework.context.listener.impl.ApplicationListenerInstanceProcessor;

public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
    private AnnotatedBeanDefinitionReader reader;
    private AnnotationConditionMatcher matcher;
    private ClassPathBeanDefinitionScanner scanner;

    public AnnotationConfigApplicationContext() {
        init();
    }

    public AnnotationConfigApplicationContext(ClassLoader classLoader, Class<?>... componentClasses) {
        super(classLoader);
        init();
        register(componentClasses);
        refresh();
    }

    public AnnotationConfigApplicationContext(ClassLoader classLoader, String... basePackages) {
        super(classLoader);
        init();
        scan(basePackages);
        refresh();
    }

    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        init();
        register(componentClasses);
        refresh();
    }

    public AnnotationConfigApplicationContext(String... basePackages) {
        init();
        scan(basePackages);
        refresh();
    }

    private void init() {
        matcher = new AnnotationConditionMatcher(this);
        reader = new AnnotatedBeanDefinitionReader(this, matcher);
        scanner = new ClassPathBeanDefinitionScanner(matcher, this);
    }

    @Override
    public void registerBeanFactoryPostProcessorBeanDefinitions() {
        super.registerBeanFactoryPostProcessorBeanDefinitions();
        register(
                ApplicationListenerInstanceProcessor.class
        );
    }

    @Override
    public void register(Class... componentClasses) {
        this.reader.register(componentClasses);
    }

    @Override
    public void scan(String... basePackages) {
        this.scanner.scan(basePackages);
    }
}
