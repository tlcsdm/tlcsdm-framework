package com.tlcsdm.framework.context_rpc;

import com.tlcsdm.framework.bean.annotation.Service;
import com.tlcsdm.framework.context.annotation.support.AbstractScanner;

public class ReferenceScanner extends AbstractScanner {

    public ReferenceScanner() {
    }

    @Override
    public void register(Class beanClass) {

    }

    @Override
    public boolean isRegisterClass(Class loaderClass) {
        return loaderClass.isInterface() && loaderClass.isAnnotationPresent(Service.class);
    }
}
