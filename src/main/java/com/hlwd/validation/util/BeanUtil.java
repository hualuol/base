package com.hlwd.validation.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class BeanUtil {
    public BeanUtil() {
    }

    public static void copy(Object from, Object to, Map<String, CopyRule> mapping) throws Exception {
        Class<?> fromClazz = from.getClass();
        Class<?> toClazz = to.getClass();
        List<Field> toFields = getFields(toClazz);
        Iterator var6 = toFields.iterator();

        while(true) {
            Field field;
            String targetFieldName;
            do {
                do {
                    while(true) {
                        do {
                            if (!var6.hasNext()) {
                                return;
                            }

                            field = (Field)var6.next();
                        } while("serialversionuid".equals(field.getName().toLowerCase()));

                        field.setAccessible(true);
                        targetFieldName = field.getName();
                        Field targetField = getField(fromClazz, targetFieldName);
                        if (targetField == null) {
                            break;
                        }

                        targetField.setAccessible(true);
                        Object targetVal = targetField.get(from);
                        field.set(to, targetVal);
                    }
                } while(mapping == null);
            } while(!mapping.keySet().contains(targetFieldName));

            CopyRule rule = (CopyRule)mapping.get(targetFieldName);
            boolean complete = true;
            Object targetVal = null;
            Field realTargetField = null;
            switch(rule.getType()) {
                case 0:
                    String realFieldName = rule.getFieldName();
                    realTargetField = getField(fromClazz, realFieldName);
                    if (realTargetField == null) {
                        complete = false;
                    } else {
                        realTargetField.setAccessible(true);
                        targetVal = realTargetField.get(from);
                    }
                    break;
                case 1:
                    List<String> materials = new ArrayList();
                    String template = rule.getTemplate();
                    String[] fields = rule.getFieldName().split(",");
                    String[] var18 = fields;
                    int var19 = fields.length;

                    for(int var20 = 0; var20 < var19; ++var20) {
                        String f = var18[var20];
                        realTargetField = getField(fromClazz, f);
                        if (realTargetField == null) {
                            complete = false;
                            break;
                        }

                        realTargetField.setAccessible(true);
                        Object tempVal = realTargetField.get(from);
                        materials.add(tempVal.toString());
                    }

                    if (complete) {
                        targetVal = String.format(template, materials.toArray());
                    }
                    break;
                default:
                    complete = false;
            }

            if (complete && targetVal != null) {
                field.set(to, targetVal);
            }
        }
    }

    public static void copyProperties(Object from, Object to, Map<String, String> mapping) throws Exception {
        Class<?> fromClazz = from.getClass();
        Class<?> toClazz = to.getClass();
        List<Field> toFields = getFields(toClazz);
        Iterator var6 = toFields.iterator();

        while(var6.hasNext()) {
            Field field = (Field)var6.next();
            if (!"serialversionuid".equals(field.getName().toLowerCase())) {
                field.setAccessible(true);
                String targetFieldName = field.getName();
                Field targetField = getField(fromClazz, targetFieldName);
                if (targetField == null) {
                    if (mapping != null && mapping.keySet().contains(targetFieldName)) {
                        String realFieldName = (String)mapping.get(targetFieldName);
                        Field realTargetField = getField(fromClazz, realFieldName);
                        if (realTargetField != null) {
                            realTargetField.setAccessible(true);
                            Object targetVal = realTargetField.get(from);
                            field.set(to, targetVal);
                        }
                    }
                } else {
                    targetField.setAccessible(true);
                    Object targetVal = targetField.get(from);
                    field.set(to, targetVal);
                }
            }
        }

    }

    public static List<Field> getFields(Class<?> entityClass) {
        ArrayList fields;
        for(fields = new ArrayList(); entityClass != Object.class; entityClass = entityClass.getSuperclass()) {
            fields.addAll(Arrays.asList(entityClass.getDeclaredFields()));
        }

        return fields;
    }

    public static Field getField(Class<?> entityClass, String fieldName) {
        Field targetField = null;
        boolean complete = false;

        while(entityClass != Object.class) {
            try {
                targetField = entityClass.getDeclaredField(fieldName);
                complete = true;
                break;
            } catch (NoSuchFieldException var5) {
                entityClass = entityClass.getSuperclass();
            }
        }

        return !complete ? null : targetField;
    }

    public static Type[] getGenericParamType(Object entity) {
        Type genType = entity.getClass().getGenericSuperclass();
        return ((ParameterizedType)genType).getActualTypeArguments();
    }
}
