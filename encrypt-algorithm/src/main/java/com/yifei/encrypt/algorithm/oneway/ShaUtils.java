package com.yifei.encrypt.algorithm.oneway;

import com.yifei.encrypt.algorithm.HexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Security;

/**
 * SHA工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 16:59
 */
@Slf4j
public class ShaUtils {

    private ShaUtils() {
    }

    /**
     * 加密
     *
     * @param shaAlgorithm sha算法
     * @param plainText    明文
     * @return String 若加密异常，则返回null
     */
    public static String encrypt(String shaAlgorithm, String plainText) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance(shaAlgorithm);
            md.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] cipherTextBytes = md.digest();
            return HexUtils.toHexString(cipherTextBytes);
        } catch (Exception e) {
            log.error("shaAlgorithm：【{}】，plainText：【{}】，SHA加密出现异常：", shaAlgorithm, plainText, e);
        }
        return null;
    }

    /**
     * 加密
     *
     * @param shaAlgorithm sha算法
     * @param plainText    明文
     * @return String 若加密异常，则返回null
     */
    public static String encryptByDigestUtils(String shaAlgorithm, String plainText) {
        MessageDigest md = DigestUtils.getDigest(shaAlgorithm);
        DigestUtils.updateDigest(md, plainText);
        byte[] cipherTextBytes = md.digest();
        return HexUtils.toHexString(cipherTextBytes);
    }

    /**
     * 加密
     *
     * @param plainText 明文
     * @return String 若加密异常，则返回null
     */
    public static String sha256Hex(String plainText) {
        return DigestUtils.sha256Hex(plainText);
    }

    /**
     * 加密
     *
     * @param inputStream 输入流
     * @return String 若加密异常，则返回null
     */
    public static String sha256Hex(InputStream inputStream) {
        try {
            return DigestUtils.sha256Hex(inputStream);
        } catch (IOException e) {
            log.error("sha256Hex加密文件出现异常：", e);
        }
        return null;
    }
}
