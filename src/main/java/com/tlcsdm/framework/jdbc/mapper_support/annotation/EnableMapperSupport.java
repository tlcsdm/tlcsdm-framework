package com.tlcsdm.framework.jdbc.mapper_support.annotation;

import com.tlcsdm.framework.bean.annotation.Import;
import com.tlcsdm.framework.jdbc.mapper_support.MapperSupportImport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MapperSupportImport.class})
public @interface EnableMapperSupport {

}
