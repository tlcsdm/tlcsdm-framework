package com.tlcsdm.framework.jdbc.mapper_support;

import java.lang.reflect.Field;
import java.util.List;

public class IdTypeAssignAuto implements IdTypeStrategy {
    @Override
    public String assembly(List args, Object entity, Field idField) {
        return null;
    }
}
