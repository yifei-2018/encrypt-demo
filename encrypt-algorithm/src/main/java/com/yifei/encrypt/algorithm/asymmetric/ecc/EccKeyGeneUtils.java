package com.yifei.encrypt.algorithm.asymmetric.ecc;

import com.yifei.encrypt.algorithm.asymmetric.KeyGenerateUtils;
import com.yifei.encrypt.algorithm.asymmetric.model.Base64KeyPair;
import com.yifei.encrypt.algorithm.constant.KeyAlgorithmEnum;
import com.yifei.encrypt.algorithm.constant.KeySizeEnum;

/**
 * ECC公私钥生成工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/2 18:13
 */
public class EccKeyGeneUtils {
    private EccKeyGeneUtils() {
    }

    /**
     * 生成公私钥文件
     * <br/>注：公私钥未进行base64编码
     *
     * @param publicKeyFilePath  公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @return boolean
     */
    public static boolean generateKeyFile(String publicKeyFilePath, String privateKeyFilePath) {
        return KeyGenerateUtils.generateKeyFile(KeyAlgorithmEnum.ECC.getAlgorithm(), KeySizeEnum.K_256.getSize(), publicKeyFilePath, privateKeyFilePath);
    }

    /**
     * 生成base64公私钥文件
     *
     * @param publicKeyFilePath  公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @return boolean
     */
    public static boolean generateBase64KeyFile(String publicKeyFilePath, String privateKeyFilePath) {
        return KeyGenerateUtils.generateBase64KeyFile(KeyAlgorithmEnum.ECC.getAlgorithm(), KeySizeEnum.K_256.getSize(), publicKeyFilePath, privateKeyFilePath);
    }

    /**
     * 生成base64公私钥文件
     *
     * @return Base64KeyPair
     */
    public static Base64KeyPair generateBase64KeyPair() {
        return KeyGenerateUtils.generateBase64KeyPair(KeyAlgorithmEnum.ECC.getAlgorithm(), KeySizeEnum.K_256.getSize());
    }
}
