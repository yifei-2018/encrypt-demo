package com.yifei.encrypt.algorithm.symmetric;

import com.sun.crypto.provider.SunJCE;
import com.yifei.encrypt.algorithm.Base64Utils;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;

/**
 * AES工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 21:46
 */
@Slf4j
public class AesUtils {
    /**
     * provider
     */
    private static final Provider PROVIDER = new SunJCE();
    /**
     * 算法：AES
     */
    private static final String ALGORITHM_AES = "AES";
    /**
     * 偏移变量,具体字节数与密钥字节数相关
     */
    private final static String IV_PARAMETER = "1234567812345678";
    /**
     * 加(/解)密算法/工作模式/填充模式<br/>
     * 注：可使用默认的工作模式和填充模式，具体配置可查询相关资料
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    private AesUtils() {
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @param secret    密钥(注：密钥24字节)
     * @return String 若加密异常，则返回null
     */
    public static String encrypt(String plainText, String secret) {
        try {
            return SymEncryptUtils.encrypt(ALGORITHM_AES, plainText, secret);
        } catch (Exception e) {
            log.error("AES加密出现异常：", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param cipherText 密文
     * @param secret     密钥（注：同加密密钥）
     * @return String
     */
    public static String decrypt(String cipherText, String secret) {
        try {
            return SymEncryptUtils.decrypt(ALGORITHM_AES, cipherText, secret);
        } catch (Exception e) {
            log.error("AES解密出现异常：", e);
        }
        return null;
    }

    /**
     * 加密（含工作/填充模式）
     *
     * @param plainText 明文
     * @param secret    密钥
     * @return String 若加密异常，则返回null
     */
    public static String encryptWithModel(String plainText, String secret) {
        Security.addProvider(new SunJCE());
        try {
            // 生成key
            SecretKey key = SymEncryptUtils.generateKey(ALGORITHM_AES, secret);
            // Cipher 负责完成加解密工作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            // 加密，结果保存进cipherByte
            byte[] cipherTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            // base64编码
            return Base64Utils.encodeString(cipherTextBytes);
        } catch (Exception e) {
            log.error("AES加密（含工作/填充模式）出现异常：", e);
        }
        return null;
    }

    /**
     * 解密（含工作/填充模式）
     *
     * @param cipherText 密文
     * @param secret     密钥（注：同加密密钥）
     * @return String
     */
    public static String decryptWithModel(String cipherText, String secret) {
        Security.addProvider(new SunJCE());
        try {
            // 生成key
            SecretKey key = SymEncryptUtils.generateKey(ALGORITHM_AES, secret);
            // Cipher 负责完成加解密工作
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, PROVIDER);
            // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示解密模式
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            // 解密，结果保存进plainTextByte
            byte[] cipherTextBytes = Base64Utils.decode(cipherText);
            byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
            return new String(plainTextBytes);
        } catch (Exception e) {
            log.error("AES解密（含工作/填充模式）出现异常：", e);
        }
        return null;
    }
}
