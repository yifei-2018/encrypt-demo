package com.yifei.encrypt.algorithm.asymmetric.rsa;

import com.sun.crypto.provider.SunJCE;
import com.yifei.encrypt.algorithm.Base64Utils;
import com.yifei.encrypt.algorithm.constant.KeyAlgorithmEnum;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.Provider;

/**
 * RSA工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 21:47
 */
@Slf4j
public class RsaUtils {
    /**
     * provider
     */
    private static final Provider PROVIDER = new SunJCE();

    private RsaUtils() {
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @param key       公/私钥
     * @return String
     */
    public static String encrypt(String plainText, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(KeyAlgorithmEnum.RSA.getAlgorithm(), PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            // base64编码
            return Base64Utils.encodeString(cipherBytes);
        } catch (Exception e) {
            log.error("RSA加密出现异常：", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param cipherText 密文
     * @param key        公/私钥
     * @return String
     */
    public static String decrypt(String cipherText, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(KeyAlgorithmEnum.RSA.getAlgorithm(), PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] cipherTextBytes = Base64Utils.decode(cipherText);
            byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
            // base64编码
            return new String(plainTextBytes);
        } catch (Exception e) {
            log.error("RSA解密出现异常：", e);
        }
        return null;
    }
}
