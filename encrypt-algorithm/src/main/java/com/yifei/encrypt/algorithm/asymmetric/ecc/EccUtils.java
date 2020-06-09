package com.yifei.encrypt.algorithm.asymmetric.ecc;

import com.yifei.encrypt.algorithm.Base64Utils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;

/**
 * ECC工具类
 * <br/>注：ECC算法在jdk1.5后加入支持，目前仅仅只能完成密钥的生成与解析。 如果想要获得ECC算法实现，需要调用硬件完成加密/解密（ECC算法相当耗费资源，如果单纯使用CPU进行加密/解密，效率低下），涉及到Java Card领域，PKCS#11。
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 21:48
 */
@Slf4j
public class EccUtils {

    private EccUtils() {
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @param key       公钥/私钥
     * @return String
     */
    public static String encrypt(String plainText, Key key) {
        try {
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] cipherBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            // base64编码
            return Base64Utils.encodeString(cipherBytes);
        } catch (Exception e) {
            log.error("ECC加密出现异常：", e);
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
            Cipher cipher = new NullCipher();
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] cipherTextBytes = Base64Utils.decode(cipherText);
            byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
            // base64编码
            return new String(plainTextBytes);
        } catch (Exception e) {
            log.error("ECC解密出现异常：", e);
        }
        return null;
    }

}
