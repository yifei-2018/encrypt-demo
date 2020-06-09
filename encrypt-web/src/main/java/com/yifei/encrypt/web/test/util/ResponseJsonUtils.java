package com.yifei.encrypt.web.test.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yifei.encrypt.web.test.model.SignResponse;
import org.apache.commons.lang.StringUtils;

/**
 * json工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/6 16:03
 */
public class ResponseJsonUtils {
    private ResponseJsonUtils() {
    }

    /**
     * 反序列化
     *
     * @param json  json串
     * @param clazz 类
     * @param <T>   泛型
     * @return ResponseResult
     */
    public static <T> SignResponse<T> parseObject(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, new TypeReference<SignResponse<T>>(clazz) {
        });
    }
}
