package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Import;
import com.tlcsdm.framework.bean.support.AnnotationBeanDefinitionBuilder;
import com.tlcsdm.framework.bean.support.Property;
import com.tlcsdm.framework.context.condition.ConditionMatcher;
import com.tlcsdm.framework.context.factory.ApplicationProcessor;
import com.tlcsdm.framework.context.factory.ConfigurableListableBeanFactory;
import com.tlcsdm.framework.context.factory.Resolver;
import com.tlcsdm.framework.core.support.AnnotationMetadata;
import com.tlcsdm.framework.core.util.AnnotationUtils;
import com.tlcsdm.framework.core.util.ReflectUtils;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.util.List;

public class ConfigurationAnnotationResolve implements Resolver {
    private ConfigurableListableBeanFactory configRegistry;

    private Class configClass;

    private ConditionMatcher conditionMatcher;

    public ConfigurationAnnotationResolve(ConfigurableListableBeanFactory configRegistry, ConditionMatcher conditionMatcher, Class configClass) {
        this.configRegistry = configRegistry;
        this.configClass = configClass;
        this.conditionMatcher = conditionMatcher;
    }

    @Override
    public void parse() {
        List<AnnotationMetadata<Import>> metadatas = AnnotationUtils.getAnnotationMetadatas(configClass, Import.class);
        for (AnnotationMetadata<Import> metadata : metadatas) {
            importProcessor(metadata);
        }
    }

    protected void importProcessor(AnnotationMetadata<Import> metadata) {
        Class[] value = metadata.getAnnotation().value();

        for (Class configClass : value) {
            if (isProcessor(configClass)) {
                String beanName = Introspector.decapitalize(configClass.getSimpleName());
                BeanDefinition beanDefinition = AnnotationBeanDefinitionBuilder.getBeanDefinition(configClass);
                if (BeanDefinitionImportRegistry.class.isAssignableFrom(configClass)) {
                    Field annotationMetadata = ReflectUtils.getField(BeanDefinitionImportRegistry.class, "annotationMetadata");
                    Property property = new Property(annotationMetadata);
                    property.setValue(metadata);
                    property.setType(annotationMetadata.getType());
                    property.setName("annotationMetadata");

                    beanDefinition.addProperty(property);
                }
                configRegistry.registerProcessor(beanName, beanDefinition);
            }
        }
    }

    protected boolean isProcessor(Class configClass) {
        return ApplicationProcessor.class.isAssignableFrom(configClass);
    }

}
