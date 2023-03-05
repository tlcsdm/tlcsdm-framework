package com.tlcsdm.framework.proxy.impl;

import com.tlcsdm.framework.context.annotation.Scanner;
import com.tlcsdm.framework.context.annotation.support.AbstractScanner;
import com.tlcsdm.framework.proxy.AspectResolve;
import com.tlcsdm.framework.proxy.annotation.Aspect;

public class AspectScanner extends AbstractScanner implements Scanner {

    public AspectScanner() {
    }

    @Override
    public void register(Class beanClass) {
        new AspectResolve(beanClass).parse();
    }

    @Override
    public boolean isRegisterClass(Class loaderClass) {
        return loaderClass.isAnnotationPresent(Aspect.class);
    }
}
