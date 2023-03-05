package com.tlcsdm.framework.context.annotation.support;

import com.tlcsdm.framework.bean.annotation.Arg;
import com.tlcsdm.framework.bean.support.Arguments;
import com.tlcsdm.framework.bean.support.GenericBeanDefinition;

import java.beans.Introspector;
import java.lang.reflect.Parameter;

public class AnnotationBeanDefinition extends GenericBeanDefinition {

    @Override
    public void setArguments(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            Arguments arguments = new Arguments();
            arguments.setType(parameter.getType());
            if (parameter.isAnnotationPresent(Arg.class)) {
                Arg annotation = parameter.getAnnotation(Arg.class);
                String name = annotation.name();
                if (name.equals("")) {
                    name = Introspector.decapitalize(parameter.getType().getSimpleName());
                }
                arguments.setName(name);
                String value = annotation.value();
                if (!value.equals("")) {
                    arguments.setValue(value);
                }
            } else {
                arguments.setName(parameter.getName());
            }
            addArguments(arguments.getName(), arguments);
        }
    }
}

