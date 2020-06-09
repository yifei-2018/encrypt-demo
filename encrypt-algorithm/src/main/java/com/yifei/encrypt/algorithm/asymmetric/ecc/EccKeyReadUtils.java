package com.yifei.encrypt.algorithm.asymmetric.ecc;

import com.yifei.encrypt.algorithm.asymmetric.KeyReadUtils;
import com.yifei.encrypt.algorithm.constant.KeyAlgorithmEnum;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ECC公私钥读取工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/3 15:07
 */
public class EccKeyReadUtils {
    private EccKeyReadUtils() {
    }

    /**
     * 获取公钥
     * <br/>注：未进行base64解码
     *
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKey(String publicKeyFilePath) {
        return KeyReadUtils.getPublicKey(publicKeyFilePath);
    }

    /**
     * 获取私钥
     * <br/>注：未进行base64解码
     *
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKey(String privateKeyFilePath) {
        return KeyReadUtils.getPrivateKey(privateKeyFilePath);
    }

    /**
     * 获取公钥
     *
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKeyByPem(String publicKeyFilePath) {
        return KeyReadUtils.getPublicKeyByPem(KeyAlgorithmEnum.ECC.getAlgorithm(), publicKeyFilePath);
    }

    /**
     * 获取私钥
     *
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKeyByPem(String privateKeyFilePath) {
        return KeyReadUtils.getPrivateKeyByPem(KeyAlgorithmEnum.ECC.getAlgorithm(), privateKeyFilePath);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥base64编码字符串
     * @return PublicKey 若异常，则返回null
     */
    public static PublicKey getPublicKeyByString(String publicKey) {
        return KeyReadUtils.getPublicKeyByString(KeyAlgorithmEnum.ECC.getAlgorithm(), publicKey);
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥base64编码字符串
     * @return PrivateKey 若异常，则返回null
     */
    public static PrivateKey getPrivateKeyByString(String privateKey) {
        return KeyReadUtils.getPrivateKeyByString(KeyAlgorithmEnum.ECC.getAlgorithm(), privateKey);
    }
}
