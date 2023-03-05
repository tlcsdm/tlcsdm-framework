package com.tlcsdm.framework.context;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.context.factory.ApplicationContext;
import com.tlcsdm.framework.context.factory.ApplicationProcessor;
import com.tlcsdm.framework.context.listener.ApplicationEvent;
import com.tlcsdm.framework.context.listener.ApplicationEventMulticaster;
import com.tlcsdm.framework.context.listener.impl.ContextRefreshedEvent;
import com.tlcsdm.framework.context.listener.impl.DefaultEventMulticaster;
import com.tlcsdm.framework.core.io.impl.ResourceLoaderManager;
import com.tlcsdm.framework.core.util.ClassUtils;

public class GenericApplicationContext extends AbstractConfigurableListableBeanFactory implements ApplicationContext {

    protected ApplicationEventMulticaster eventMulticaster;

    public GenericApplicationContext() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public GenericApplicationContext(ClassLoader classLoader) {
        Thread.currentThread().setContextClassLoader(classLoader);
        initialize();
    }

    protected void initialize() {

        eventMulticaster = new DefaultEventMulticaster();

        addSingletonBeans(this, eventMulticaster, ClassUtils.getDefaultClassLoader(), new ResourceLoaderManager());
    }

    public void refresh() {

        //初始化注册BeanFactoryPostProcessor
        registerBeanFactoryPostProcessorBeanDefinitions();

        preInstantiateFactoryPostProcessors();
        //执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors();

        preInstantiateSingletons();
    }

    public void registerBeanFactoryPostProcessorBeanDefinitions() {
        Class[] factoryClasses = FactoryLoader.load(ApplicationProcessor.class).getFactoryClasses();
        register(factoryClasses);
    }

    private void preInstantiateFactoryPostProcessors() {
        String[] beanDefinitionNames = this.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
            registerProcessor(beanName, beanDefinition);
        }
    }

    private void preInstantiateSingletons() {
        String[] beanDefinitionNames = this.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames) {
            BeanDefinition beanDefinition = this.getBeanDefinition(beanName);
            if (!beanDefinition.isSingleton() || beanDefinition.isLazyInit()) {
                continue;
            }
            this.getSingletonBean(beanName, beanDefinition);
        }
        this.publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void register(Class... componentClasses) {
        for (Class componentClass : componentClasses) {
            this.registerBeanDefinition(componentClass);
        }
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        eventMulticaster.multicastEvent(event);
    }

}
