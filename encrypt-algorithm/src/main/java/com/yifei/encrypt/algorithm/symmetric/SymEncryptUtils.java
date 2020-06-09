package com.yifei.encrypt.algorithm.symmetric;

import com.yifei.encrypt.algorithm.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 对称加密工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/24 19:44
 */
public class SymEncryptUtils {

    private SymEncryptUtils() {
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @param secret    密钥(注：密钥至少8字节，建议8的倍数)
     * @return String 若加密异常，则返回null
     */
    public static String encrypt(String algorithm, String plainText, String secret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 生成key
        SecretKey key = generateKey(algorithm, secret);
        // Cipher 负责完成加解密工作
        Cipher cipher = Cipher.getInstance(algorithm);
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 加密，结果保存进cipherByte
        byte[] cipherTextBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        // base64编码
        return Base64Utils.encodeString(cipherTextBytes);
    }

    /**
     * 解密
     *
     * @param cipherText 密文
     * @param secret     密钥（注：同加密密钥）
     * @return String
     */
    public static String decrypt(String algorithm, String cipherText, String secret) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // 生成key
        SecretKey key = generateKey(algorithm, secret);
        // Cipher 负责完成加解密工作
        Cipher cipher = Cipher.getInstance(algorithm);
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示解密模式
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 解密，结果保存进plainTextByte
        byte[] cipherTextBytes = Base64Utils.decode(cipherText);
        byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
        return new String(plainTextBytes);
    }

    /**
     * 生成key
     *
     * @param algorithm 算法
     * @param secret    密钥
     * @return SecretKey
     * @throws UnsupportedEncodingException
     */
    public static SecretKey generateKey(String algorithm, String secret) throws UnsupportedEncodingException {
        // 生成密钥key
        return new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm);
    }

}
