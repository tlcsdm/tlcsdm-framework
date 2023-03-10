package com.tlcsdm.framework.jdbc.mapper_support;

import com.tlcsdm.framework.core.util.ClassUtils;
import com.tlcsdm.framework.core.util.ReflectUtils;
import com.tlcsdm.framework.jdbc.BeanPropertyRowMapper;
import com.tlcsdm.framework.jdbc.ColumnMapRowMapper;
import com.tlcsdm.framework.jdbc.RowMapper;
import com.tlcsdm.framework.jdbc.SingleColumnRowMapper;
import com.tlcsdm.framework.jdbc.mapper_support.annotation.SQL;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class MapperParser {
    private static final Map<String, Properties> PROPERTY_MAP = new ConcurrentHashMap<>();

    public static Map<Method, SqlStatement> parse(Class daoClass) {
        Map<Method, SqlStatement> sqlStatements = new ConcurrentHashMap<>();
        Method[] methods = daoClass.getMethods();
        String className = daoClass.getName();
        for (Method method : methods) {
            if (method.isAnnotationPresent(SQL.class)) {
                try {
                    sqlStatements.put(method, doParse(method, className));
                } catch (Exception e) {
                    sqlStatements.put(method, doParseDynamic(method, daoClass));
                }

            }
        }
        return sqlStatements;
    }

    private static SqlStatement doParseDynamic(Method method, Class mapperClass) {
        SQL sqlAnno = method.getAnnotation(SQL.class);

        String dynamicSqlStaticMethodName = sqlAnno.dynamicSqlStaticMethodName();
        String staticMethodName = dynamicSqlStaticMethodName.equals("") ? method.getName() + "Sql" : dynamicSqlStaticMethodName;

        List<Class> methodTypes = new ArrayList<>(Arrays.asList(method.getParameterTypes()));
        methodTypes.add(List.class);
        Class[] staticMethodTypes = methodTypes.toArray(new Class[methodTypes.size()]);

        Class dynamicClass = sqlAnno.dynamicSqlMethodClass();
        if (dynamicClass.equals(SQL.class)) {
            dynamicClass = mapperClass;
        }

        Method staticMethod = ReflectUtils.getMethod(dynamicClass, staticMethodName, staticMethodTypes);

        if (!isSqlStaticMethod(staticMethod)) {
            throw new IllegalArgumentException(staticMethod.getName() + " : ??????Sql?????????????????????,?????????String??????");
        }

        Method finalStaticMethod = staticMethod;
        Function<Object[], String> sqlFunction = pars -> (String) ReflectUtils.invokeMethod(finalStaticMethod, (Object) null, pars);

        return new SqlStatement(sqlFunction);

    }

    private static boolean isSqlStaticMethod(Method staticMethod) {
        return staticMethod.getReturnType().equals(String.class);
    }

    private static SqlStatement doParse(Method method, String className) {
        String sql = method.getAnnotation(SQL.class).value();
        if (sql.equals("")) {
            sql = getSqlByFile(method.getName(), className);
        }

        SqlStatement sqlStatement = new SqlStatement(sql);
        doParseBySql(sqlStatement, method, sql);

        return sqlStatement;
    }

    private static String getSqlByFile(String methodName, String className) {
        Properties properties;
        if (PROPERTY_MAP.containsKey(className)) {
            properties = PROPERTY_MAP.get(className);
        } else {
            properties = new Properties();
            try {
                properties.load(ClassUtils.getDefaultClassLoader().getResourceAsStream(className.replace('.', File.separatorChar) + ".properties"));
            } catch (Exception e) {
                throw new RuntimeException("?????????: " + className + " ?????????" + methodName + " ???????????????Sql?????? ");
            }
            PROPERTY_MAP.put(className, properties);
        }
        String property = properties.getProperty(methodName);
        if (property != null) {
            return property;
        }
        throw new NullPointerException("?????????:" + className + " ??? " + methodName + " ???????????????Sql");
    }

    public static void doParseBySql(SqlStatement sqlStatement, Method method, String sql) {
        Class<?> returnType = method.getReturnType();
        sql = sql.trim();
        int endIndex = sql.indexOf(' ');
        //??????sql???????????????????????????????????????select
        boolean isQuery = sql.substring(0, endIndex).equalsIgnoreCase("select");
        //?????????????????????????????????????????????List??????List?????????,?????????????????????????????????,isDefaultReturnType=ture
        //????????????????????????????????????????????????int???Integer??????,?????????????????????????????????,isDefaultReturnType=ture
        boolean isDefaultReturnType = isQuery ?
                List.class.isAssignableFrom(returnType) :
                returnType.equals(Integer.class) || returnType.equals(int.class);
        //???????????????sql?????????????????????????????????????????????(List??????List?????????),?????????????????????class?????????List???????????????
        if (isQuery && isDefaultReturnType && !Map.class.isAssignableFrom(returnType)) {
            returnType = (Class) ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
        }
        RowMapper rowMapper = null;
        //?????????sql?????????sql
        if (isQuery) {
            //?????????????????????????????????????????????????????????,?????????????????????????????????,????????????Bean??????????????????
            rowMapper =
                    ReflectUtils.isPrimitive(returnType) ?
                            new SingleColumnRowMapper(returnType) :
                            Map.class.isAssignableFrom(returnType) ?
                                    new ColumnMapRowMapper() :
                                    new BeanPropertyRowMapper<>(returnType);
        }
        sqlStatement.setReturnType(returnType);
        sqlStatement.setQuery(isQuery);
        sqlStatement.setRowMapper(rowMapper);
        sqlStatement.setDefaultReturnType(isDefaultReturnType);
    }
}
