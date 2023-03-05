package com.tlcsdm.framework.context.env.impl;

import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.context.env.Environment;
import com.tlcsdm.framework.context.env.PropertyParser;

public class PropertiesPropertyParser implements PropertyParser<PropertiesPropertySource> {
    @Autowired
    private Environment environment;

    @Override
    public void parse(PropertiesPropertySource propertySource) {

    }

}
