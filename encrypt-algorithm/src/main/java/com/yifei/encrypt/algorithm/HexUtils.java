package com.yifei.encrypt.algorithm;

/**
 * 十六进制工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 20:54
 */
public class HexUtils {
    private HexUtils() {
    }

    /**
     * 转十六进制
     *
     * @param bytes 字节数组
     * @return String
     */
    public static String toHexString(byte[] bytes) {
        if (bytes == null || bytes.length < 1) {
            return "";
        }

        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes) {
            // 十进制转十六进制，X表示以十六进制形式输出，02表示不足两位，左侧补0输出
            strBuilder.append(String.format("%02X", b));
        }
        return strBuilder.toString();
    }
}
