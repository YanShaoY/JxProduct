package com.vdin.JxProduct.Util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StringUtils {

    public static boolean isEmpty(String string) {
        if (string == null || string.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String cutStrToName(String name) {

        String countStr = name.substring(0, 1);
        for (int i = 0; i < name.length() - 1; i++) {
            countStr = countStr + "*";
        }
        return countStr;
    }

    /**
     * Object 转Map
     *
     * @param obj 传入对象
     * @return 转换后对象
     */
    public static Map<String, Object> ObjectToMapUtil(Object obj) {

        if (obj == null) {
            return null;
        }

        if (!(obj instanceof Map)) {
            return null;
        }

        Map<String, Object> reMap = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                reMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return reMap;
    }

    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field subField = null;
            try {
                subField = obj.getClass().getDeclaredField(fields[i].getName());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            subField.setAccessible(true);
            Object o = null;
            try {
                o = subField.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            reMap.put(fields[i].getName(), o);
        }
        return reMap;
    }


}
