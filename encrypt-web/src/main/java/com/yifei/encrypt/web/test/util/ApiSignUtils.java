package com.yifei.encrypt.web.test.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * api签名工具类
 *
 * @author luqs
 * @version v1.0.0
 * @date 2020-03-12 下午1:05:26
 */
@Slf4j
public class ApiSignUtils {
    /**
     * 字段：serialVersionUID
     */
    private static final String FIELD_SERIAL_VERSION_UID = "serialVersionUID";

    private ApiSignUtils() {
    }

    /**
     * 获取对象字段Map
     *
     * @param object 对象
     * @return Map<String, Object>
     */
    public static Map<String, Object> getFieldMap(Object object, String... ignoreFields) {
        List<Field> fieldList = getFieldList(object);
        Map<String, Object> filedMap = new HashMap<String, Object>(16);
        String fieldName;
        try {
            for (Field field : fieldList) {
                fieldName = field.getName();
                // 排除serialVersionUID字段
                if (FIELD_SERIAL_VERSION_UID.equals(fieldName)) {
                    continue;
                }
                // 排除忽略字段
                if (ArrayUtils.contains(ignoreFields, fieldName)) {
                    continue;
                }
                field.setAccessible(true);
                filedMap.put(fieldName, field.get(object));
            }
            return filedMap;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.error("参数：【{}】，获取对象字段Map出现异常：", JSON.toJSONString(object), e);
        }
        return null;
    }

    /**
     * 拼接参数串<br/>
     * 注：1.以key升序排列；2.value为空、null或空格，则不拼接；
     *
     * @param fieldMap    字段map
     * @param secretField 密钥参数名
     * @param secret      密钥
     * @return String
     */
    public static String appendParamStr(Map<String, Object> fieldMap, String secretField, String secret) {
        // 添加密钥字段
        Map<String, Object> appendFieldMap = new HashMap<String, Object>(16);
        appendFieldMap.putAll(fieldMap);
        appendFieldMap.put(secretField, secret);
        return appendParamStr(appendFieldMap);
    }

    /**
     * 拼接参数串<br/>
     * 注：1.以key升序排列；2.value为空、null或空格，则不拼接；
     *
     * @param fieldMap 字段Map
     * @return String
     */
    public static String appendParamStr(Map<String, Object> fieldMap) {
        List<String> fieldNameList = new ArrayList<>();
        for (String fieldName : fieldMap.keySet()) {
            fieldNameList.add(fieldName);
        }
        // 排序
        Collections.sort(fieldNameList);

        // 拼接参数串
        StringBuilder paramStrBuilder = new StringBuilder();
        Object fieldVal;
        for (String fieldNameStr : fieldNameList) {
            fieldVal = fieldMap.get(fieldNameStr);
            // 过滤掉空值
            if (fieldVal == null || "".equals(String.valueOf(fieldVal).trim())) {
                continue;
            }
            paramStrBuilder.append(fieldNameStr).append("=").append(fieldVal).append("&");
        }
        String requestParamStr = paramStrBuilder.substring(0, paramStrBuilder.length() - 1);
        log.debug("待签名的参数串：【{}】", requestParamStr);
        return requestParamStr;
    }

    /**
     * 获取类字段（含所有父类字段）
     *
     * @param object 对象
     * @return List<Field>
     */
    private static List<Field> getFieldList(Object object) {
        List<Field> fieldList = new ArrayList<>();
        Class tempClass = object.getClass();
        // 当父类为null的时候说明到达了最上层的父类(Object类)
        while (tempClass != null) {
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            // 得到父类,然后赋给自己
            tempClass = tempClass.getSuperclass();
        }
        return fieldList;
    }

}
