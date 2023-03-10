package com.tlcsdm.framework.context_rpc;

import com.tlcsdm.framework.bean.annotation.Component;
import com.tlcsdm.framework.bean.annotation.Service;
import com.tlcsdm.framework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Service
@Component
public @interface RpcService {
    @AliasFor(annotation = Component.class)
    String value() default "";

    String protocolName() default "BioSocket";

    String hostname() default "localhost";

    int port() default 8080;

    String version() default "";

}
