package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.BeanDefinition;
import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.bean.annotation.PropertySource;
import com.tlcsdm.framework.bean.support.AnnotationBeanDefinitionBuilder;
import com.tlcsdm.framework.context.exception.BeansException;
import com.tlcsdm.framework.context.factory.BeanDefinitionRegistry;
import com.tlcsdm.framework.core.io.ResourceLoader;
import com.tlcsdm.framework.core.support.AnnotationMetadata;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropertySourceConfig extends BeanDefinitionImportRegistry {

    private Map<String, Properties> propertyMap;
    @Autowired
    private ResourceLoader loader;

    @Override
    public void postProcessBeanDefinitionRegistry(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) throws BeansException {
        if (propertyMap == null) {
            PropertySource propertySource = (PropertySource) annotationMetadata.getAnnotationInSrc(PropertySource.class);
            init(propertySource.value());
        }
        for (Map.Entry<String, Properties> propertiesEntry : propertyMap.entrySet()) {
            Properties properties = propertiesEntry.getValue();
            String path = propertiesEntry.getKey();
            regisBean(path, properties, registry);
        }
    }

    private void init(String[] paths) {
        propertyMap = new ConcurrentHashMap<>();
        for (String path : paths) {
            Properties properties = new Properties();
            try (Reader reader = loader.getResourceHolder(path).getReader()) {
                properties.load(reader);
            } catch (IOException e) {
                throw new RuntimeException("找不到路径名为: " + path + " 的文件");
            }
            propertyMap.put(path.substring(0, path.lastIndexOf('.')), properties);
        }
    }

    private void regisBean(String path, Properties properties, BeanDefinitionRegistry registry) {
        for (String keyName : properties.stringPropertyNames()) {
            BeanDefinition beanDefinition = AnnotationBeanDefinitionBuilder.getBeanDefinition(String.class, () -> properties.getProperty(keyName));
            registry.registerBeanDefinition(keyName, beanDefinition);
        }
        BeanDefinition propertyDefinition = AnnotationBeanDefinitionBuilder.getBeanDefinition(Properties.class, () -> properties);
        registry.registerBeanDefinition(path, propertyDefinition);
    }

}
