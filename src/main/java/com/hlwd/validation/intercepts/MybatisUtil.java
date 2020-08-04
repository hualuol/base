package com.hlwd.validation.intercepts;


import com.hlwd.util.db.CustomSqlSessionTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class MybatisUtil {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired(
            required = false
    )
    private CustomSqlSessionTemplate template;
    @Qualifier("sqlSessionFactory")
    private SqlSessionFactoryBean sqlSessionFactoryBean;

    public MybatisUtil() {
    }

    public Map<String, Object> makeBindObject(Object[] names, Object[] values) {
        Map<String, Object> map = new HashMap();
        int index = 0;
        if (names != null) {
            Object[] var5 = names;
            int var6 = names.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Object p = var5[var7];
                Object value = values[index];
                ++index;
                map.put(p.toString(), value);
                map.put("param" + index, value);
            }
        }

        return map;
    }

    public Object[] getParameterNames(Method targetMethod) {
        Annotation[][] as = targetMethod.getParameterAnnotations();
        List<String> anns = new ArrayList();

        for(int i = 0; i < as.length; ++i) {
            int length = as[i].length;
            if (length != 0) {
                for(int j = 0; j < length; ++j) {
                    Param pa = (Param)as[i][j];
                    anns.add(pa.value());
                }
            }
        }

        Object[] parameterNames = anns.toArray();
        return parameterNames;
    }

    public String getSQL(String methodName, Map<String, Object> bindObject) throws Exception {
        Configuration configuration = null;
        if (this.template != null) {
            configuration = this.template.getSqlSessionFactory().getConfiguration();
        } else {
            configuration = this.sqlSessionFactoryBean.getObject().getConfiguration();
        }

        MappedStatement statement = configuration.getMappedStatement(methodName);
        BoundSql boundSql = statement.getBoundSql(bindObject);
        return getSql(boundSql, bindObject, configuration);
    }

    public static String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null) {
            for(int i = 0; i < parameterMappings.size(); ++i) {
                ParameterMapping parameterMapping = (ParameterMapping)parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    String propertyName = parameterMapping.getProperty();
                    Object value;
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }

                    sql = replacePlaceholder(sql, value);
                }
            }
        }

        return sql;
    }

    public static String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (propertyValue != null) {
            if (propertyValue instanceof String) {
                result = "'" + propertyValue + "'";
            } else if (propertyValue instanceof Date) {
                result = "'" + DATE_FORMAT.format(propertyValue) + "'";
            } else {
                result = propertyValue.toString();
            }
        } else {
            result = "null";
        }

        return sql.replaceFirst("\\?", result);
    }
}
