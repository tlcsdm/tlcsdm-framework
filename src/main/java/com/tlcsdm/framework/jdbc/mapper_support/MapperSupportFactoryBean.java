package com.tlcsdm.framework.jdbc.mapper_support;

import com.tlcsdm.framework.bean.annotation.Autowired;
import com.tlcsdm.framework.context.factory.FactoryBean;

public class MapperSupportFactoryBean implements FactoryBean {

    @Autowired
    private MapperSupportFactory mapperSupportFactory;

    @Override
    public Object getObject(Class mapperClass) {
        return mapperSupportFactory.getMapper(mapperClass);
    }

    @Override
    public Class getObjectType() {
        return MapperSupport.class;
    }

}
