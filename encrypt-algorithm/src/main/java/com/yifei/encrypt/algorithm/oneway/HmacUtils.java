package com.yifei.encrypt.algorithm.oneway;

import com.yifei.encrypt.algorithm.HexUtils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HMAC工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 20:25
 */
@Slf4j
public class HmacUtils {

    private HmacUtils() {
    }

    /**
     * 加密
     *
     * @param hmacAlgorithm hmac算法
     * @param plainText     明文
     * @param secret        密钥
     * @return String
     */
    public static String encrypt(String hmacAlgorithm, String plainText, String secret) {
        try {
            // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), hmacAlgorithm);
            // 生成一个指定 Mac 算法对象
            Mac mac = Mac.getInstance(hmacAlgorithm);
            // 用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            // 完成 Mac 操作
            byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] cipherTextBytes = mac.doFinal(plainTextBytes);
            return HexUtils.toHexString(cipherTextBytes);
        } catch (Exception e) {
            log.error("hmacAlgorithm：【{}】,plainText：【{}】，HMAC加密出现异常：", hmacAlgorithm, plainText, e);
        }
        return null;
    }
}
