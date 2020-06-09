package com.yifei.encrypt.algorithm;

import org.apache.commons.codec.binary.Base64;

/**
 * base64工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/24 11:54
 */
public class Base64Utils {
    private Base64Utils() {
    }

    /**
     * 编码
     *
     * @param bytes 字节数组
     * @return String
     */
    public static String encodeString(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 编码
     *
     * @param bytes 字节数组
     * @return String
     */
    public static byte[] encode(byte[] bytes) {
        return Base64.encodeBase64(bytes);
    }

    /**
     * 解码
     *
     * @param base64String base64字符串
     * @return byte[]
     */
    public static byte[] decode(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    /**
     * 解码
     *
     * @param bytes base64字节数组
     * @return byte[]
     */
    public static byte[] decode(byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }
}
