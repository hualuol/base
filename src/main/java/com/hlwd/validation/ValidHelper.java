package com.hlwd.validation;

import com.hlwd.entity.Msg;
import com.hlwd.exception.LibException;
import com.hlwd.validation.annotation.IsNotNull;
import com.hlwd.validation.annotation.Len;
import com.hlwd.validation.annotation.Match;
import com.hlwd.validation.util.BeanUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidHelper {
    public ValidHelper() {
    }

    public static boolean isXssOrSQLInject(Object entity, Msg msg) {
        return isSQLInject(entity, msg) || isXSS(entity, msg);
    }

    public static boolean isValid(Object entity, String fieldStr, Msg msg) throws Exception {
        boolean validFlag = true;
        List<String> updateFields = Arrays.asList(fieldStr.split(","));
        Class<?> entityClass = entity.getClass();
        List<Field> fields = BeanUtil.getFields(entityClass);
        Iterator var7 = fields.iterator();

        while(var7.hasNext()) {
            Field field = (Field)var7.next();

            try {
                field.setAccessible(true);
                Object value;
                if (field.getType() != List.class) {
                    long count = updateFields.stream().filter((f) -> {
                        return f.equals(field.getName());
                    }).count();
                    if (count > 0L) {
                        value = field.get(entity);
                        if (value != null && (isSQLInject(value, msg) || !annotationValid(entity, field, msg))) {
                            validFlag = false;
                            break;
                        }
                    }
                } else {
                    List<?> values = (List)field.get(entity);
                    if (values != null) {
                        Iterator var10 = values.iterator();

                        while(var10.hasNext()) {
                            value = var10.next();
                            if (!isValid(value, fieldStr, msg)) {
                                validFlag = false;
                                break;
                            }
                        }

                        if (!validFlag) {
                            break;
                        }
                    }
                }
            } catch (Exception var12) {
                throw new LibException(var12, String.format("【参数校验】成员%s处理异常", field.getName()));
            }
        }

        return validFlag;
    }

    public static boolean isXSS(Object value, Msg msg) {
        if (String.class != value.getClass() || value.toString().indexOf("<script>") < 0 && value.toString().indexOf("</script>") < 0) {
            return false;
        } else {
            msg.setContent("请求参数有XSS风险，请不要使用敏感词");
            return true;
        }
    }

    public static boolean isSQLInject(Object value, Msg msg) {
        String reg = "([\\w\\W]*)\\$([\\w\\W]*)|(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(sleep|select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        if (String.class == value.getClass()) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(value.toString());
            if (matcher.matches()) {
                msg.setContent("请求参数有SQL注入风险，请不要使用敏感词");
                return true;
            }
        }

        return false;
    }

    public static boolean annotationValid(Object entity, Field field, Msg msg) throws Exception {
        boolean validFlag = true;
        Annotation[] anns = field.getAnnotations();
        Annotation[] var5 = anns;
        int var6 = anns.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Annotation ann = var5[var7];
            String value = field.get(entity).toString();
            Pattern pattern;
            Matcher matcher;
            if (ann.annotationType() == Match.class) {
                Match specificAnn = (Match)ann;
                pattern = Pattern.compile(specificAnn.regex());
                matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    validFlag = false;
                    msg.setContent("[" + entity.getClass().getSimpleName().toLowerCase() + "-" + field.getName() + "]：" + value + ", " + specificAnn.message());
                    msg.setTime(new Date());
                    break;
                }
            } else if (ann.annotationType() == Len.class) {
                Len specificAnn = (Len)ann;
                pattern = Pattern.compile(String.format("\\S{%d,%d}", specificAnn.min(), specificAnn.max()));
                matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    validFlag = false;
                    msg.setContent("[" + entity.getClass().getSimpleName().toLowerCase() + "-" + field.getName() + "]：" + value + ", " + specificAnn.message());
                    msg.setTime(new Date());
                    break;
                }
            } else if (ann.annotationType() == IsNotNull.class) {
                IsNotNull specificAnn = (IsNotNull)ann;
                if (value == null || "".equals(value.trim())) {
                    validFlag = false;
                    msg.setContent("[" + entity.getClass().getSimpleName().toLowerCase() + "-" + field.getName() + "]：" + value + ", " + specificAnn.message());
                    msg.setTime(new Date());
                    break;
                }
            }
        }

        return validFlag;
    }
}