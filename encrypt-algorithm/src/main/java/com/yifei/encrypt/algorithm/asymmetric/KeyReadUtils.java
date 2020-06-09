package com.yifei.encrypt.algorithm.asymmetric;

import com.yifei.encrypt.algorithm.Base64Utils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 公私钥读取工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/1 11:48
 */
@Slf4j
public class KeyReadUtils {

    private KeyReadUtils() {
    }

    /**
     * 获取公钥
     * <br/>注：未进行base64解码
     *
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKey(String publicKeyFilePath) {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(publicKeyFilePath)))
        ) {
            return (PublicKey) inputStream.readObject();
        } catch (Exception e) {
            log.error("获取公钥【{}】出现异常：", publicKeyFilePath, e);
        }
        return null;
    }

    /**
     * 获取私钥
     * <br/>注：未进行base64解码
     *
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKey(String privateKeyFilePath) {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(privateKeyFilePath)))
        ) {
            return (PrivateKey) inputStream.readObject();
        } catch (Exception e) {
            log.error("获取私钥【{}】出现异常：", privateKeyFilePath, e);
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param algorithm         算法
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKeyByPem(String algorithm, String publicKeyFilePath) {
        String keyString = readKeyFile(publicKeyFilePath);
        return getPublicKeyByString(algorithm, keyString);
    }

    /**
     * 获取私钥
     *
     * @param algorithm          算法
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKeyByPem(String algorithm, String privateKeyFilePath) {
        String keyString = readKeyFile(privateKeyFilePath);
        return getPrivateKeyByString(algorithm, keyString);
    }

    /**
     * 获取公钥
     *
     * @param algorithm 算法
     * @param publicKey 公钥base64编码字符串
     * @return PublicKey 若异常，则返回null
     */
    public static PublicKey getPublicKeyByString(String algorithm, String publicKey) {
        byte[] bytes = Base64Utils.decode(publicKey);
        return getPublicKey(algorithm, bytes);
    }

    /**
     * 获取私钥
     *
     * @param algorithm  算法
     * @param privateKey 私钥base64编码字符串
     * @return PrivateKey 若异常，则返回null
     */
    public static PrivateKey getPrivateKeyByString(String algorithm, String privateKey) {
        byte[] bytes = Base64Utils.decode(privateKey);
        return getPrivateKey(algorithm, bytes);
    }

    /**
     * 获取公钥
     *
     * @param algorithm 算法
     * @param keyBytes  公钥字节数组
     * @return PublicKey 若异常，则返回null
     */
    public static PublicKey getPublicKey(String algorithm, byte[] keyBytes) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            log.error("获取私钥出现异常：", e);
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param keyBytes 私钥字节数组
     * @return PrivateKey 若异常，则返回null
     */
    public static PrivateKey getPrivateKey(String algorithm, byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            log.error("获取私钥出现异常：", e);
        }
        return null;
    }

    /**
     * 读取公私钥文件
     *
     * @param filePath 文件路径(.pem)
     * @return String base64编码
     */
    private static String readKeyFile(String filePath) {
        try (
                // 读取pem文件
                BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        ) {
            String lineStr;
            StringBuilder keyStrBuilder = new StringBuilder();
            while ((lineStr = bufferedReader.readLine()) != null) {
                // 去除开头（-----BEGIN *** KEY-----）和结尾（-----END *** KEY-----）
                if (lineStr.startsWith("--") && lineStr.endsWith("--")) {
                    continue;
                }
                keyStrBuilder.append(lineStr);
            }

            return keyStrBuilder.toString();
        } catch (Exception e) {
            log.error("读取公私钥文件【{}】出现异常：", filePath, e);
        }
        return null;
    }
}
