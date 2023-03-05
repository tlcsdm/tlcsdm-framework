package com.tlcsdm.framework.context.env;

public interface Environment extends PropertyParser, PropertyHolder {

    void registerPropertyParser(PropertyParser propertyParser);
}