package com.tlcsdm.framework.core.support.impl;

import com.tlcsdm.framework.core.annotation.Order;
import com.tlcsdm.framework.core.util.AnnotationUtils;

import java.util.List;

public class AnnotationOrderComparator extends OrderComparator {
    public static final AnnotationOrderComparator INSTANCE = new AnnotationOrderComparator();

    public static void sort(List list) {
        list.sort(INSTANCE);
    }

    @Override
    protected int getOrder(Object object) {
        return AnnotationUtils.getValue(object.getClass(), Order.class, 1);
    }
}