package com.tlcsdm.framework.context_rpc;

import com.tlcsdm.framework.bean.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ReferencePostProcessor.class)
public @interface EnableRpc {

}
