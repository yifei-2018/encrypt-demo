package com.yifei.encrypt.algorithm.oneway;


import com.yifei.encrypt.algorithm.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 15:28
 */
@Slf4j
public class Md5Utils {

    private Md5Utils() {
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @return String 若加密异常，则返回null
     */
    public static String encrypt(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] cipherTextBytes = md.digest();
            return HexUtils.toHexString(cipherTextBytes);
        } catch (Exception e) {
            log.error("plainText：【{}】，MD5加密出现异常：", plainText, e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @return String 若加密异常，则返回null
     */
    public static String md5Hex(String plainText) {
        return DigestUtils.md5Hex(plainText);
    }

    /**
     * 加密
     *
     * @param inputStream 输入流
     * @return String 若加密异常，则返回null
     */
    public static String md5Hex(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            log.error("md5Hex加密文件出现异常：", e);
        }
        return null;
    }
}
