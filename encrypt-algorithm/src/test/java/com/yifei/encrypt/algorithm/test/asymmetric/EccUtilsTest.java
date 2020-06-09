package com.yifei.encrypt.algorithm.test.asymmetric;

import com.yifei.encrypt.algorithm.asymmetric.ecc.EccKeyGeneUtils;
import com.yifei.encrypt.algorithm.asymmetric.ecc.EccKeyReadUtils;
import com.yifei.encrypt.algorithm.asymmetric.ecc.EccUtils;
import com.yifei.encrypt.algorithm.asymmetric.model.Base64KeyPair;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * ECC工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/29 00:03
 */
@Slf4j
public class EccUtilsTest {
    /**
     * 公钥文件路径
     */
    private static final String PUB_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\ecc_pubKey.key";
    /**
     * 私钥文件路径
     */
    private static final String PRI_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\ecc_priKey.key";
    /**
     * 公钥[base64]文件路径
     */
    private static final String PUB_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\ecc_pubKey_base64.key";
    /**
     * 私钥[base64]文件路径
     */
    private static final String PRI_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\ecc_priKey_base64.key";

    @Before
    public void init() {
    }

    @Test
    public void encryptTest() {
        // 生成公私钥文件
        boolean genFlag = EccKeyGeneUtils.generateKeyFile(PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);
        log.info("ECC公私钥文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genFlag, PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥文件加解密-公钥加密、私钥解密 */
        log.info("**************** ECC[公钥]公钥加密、私钥解密 ****************");
        PublicKey publicKey = EccKeyReadUtils.getPublicKey(PUB_KEY_FILE_PATH);
        log.info("ECC[公钥]加密-明文：【{}】", plainText);
        String cipherText = EccUtils.encrypt(plainText, publicKey);
        log.info("ECC[公钥]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = EccKeyReadUtils.getPrivateKey(PRI_KEY_FILE_PATH);
        log.info("ECC[私钥]解密-密文：【{}】", cipherText);
        String plainText1 = EccUtils.decrypt(cipherText, privateKey);
        log.info("ECC[私钥]解密-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);

        /* 公私钥文件加解密-私钥加密、公钥解密 */
        log.info("**************** ECC[私钥]私钥加密、公钥解密 ****************");
        privateKey = EccKeyReadUtils.getPrivateKey(PRI_KEY_FILE_PATH);
        log.info("ECC[私钥]加密-明文：【{}】", plainText);
        cipherText = EccUtils.encrypt(plainText, privateKey);
        log.info("ECC[私钥]加密-密文：【{}】", cipherText);

        publicKey = EccKeyReadUtils.getPublicKey(PUB_KEY_FILE_PATH);
        log.info("ECC[公钥]解密-密文：【{}】", cipherText);
        String plainText3 = EccUtils.decrypt(cipherText, publicKey);
        log.info("ECC[公钥]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }

    @Test
    public void encryptByBase64FileTest() {
        // 生成公私钥[base64]文件
        boolean genBase64Flag = EccKeyGeneUtils.generateBase64KeyFile(PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);
        log.info("ECC公私钥[base64]文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genBase64Flag, PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥base64文件加解密-公钥加密、私钥解密 */
        log.info("**************** ECC[公钥base64文件]公钥加密、私钥解密 ****************");
        PublicKey publicKey = EccKeyReadUtils.getPublicKeyByPem(PUB_KEY_BASE64_FILE_PATH);
        log.info("ECC[公钥base64文件]加密-明文：【{}】", plainText);
        String cipherText = EccUtils.encrypt(plainText, publicKey);
        log.info("ECC[公钥base64文件]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = EccKeyReadUtils.getPrivateKeyByPem(PRI_KEY_BASE64_FILE_PATH);
        log.info("ECC[私钥base64文件]解密-密文：【{}】", cipherText);
        String plainText2 = EccUtils.decrypt(cipherText, privateKey);
        log.info("ECC[私钥base64文件]解密-明文：【{}】", plainText2);
        Assert.assertEquals(plainText, plainText2);

        /* 公私钥base64文件加解密-私钥加密、公钥解密 */
        log.info("**************** ECC[私钥base64文件]私钥加密、公钥解密 ****************");
        privateKey = EccKeyReadUtils.getPrivateKeyByPem(PRI_KEY_BASE64_FILE_PATH);
        log.info("ECC[私钥base64文件]加密-明文：【{}】", plainText);
        cipherText = EccUtils.encrypt(plainText, privateKey);
        log.info("ECC[私钥base64文件]加密-密文：【{}】", cipherText);

        publicKey = EccKeyReadUtils.getPublicKeyByPem(PUB_KEY_BASE64_FILE_PATH);
        log.info("ECC[公钥base64文件]解密-密文：【{}】", cipherText);
        String plainText3 = EccUtils.decrypt(cipherText, publicKey);
        log.info("ECC[公钥base64文件]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }

    @Test
    public void encryptByBase64StringTest() {
        // 生成公私钥对[base64]
        Base64KeyPair keyPair = EccKeyGeneUtils.generateBase64KeyPair();
        String pubKeyBase64Str = keyPair.getPublicKey();
        String priKeyBase64Str = keyPair.getPrivateKey();
        log.info("ECC公私钥[base64]字符串生成结果：【{}】\n公钥：【{}】\n私钥：【{}】", StringUtils.isNotBlank(pubKeyBase64Str), pubKeyBase64Str, priKeyBase64Str);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥base64字符串加解密-公钥加密、私钥解密 */
        log.info("**************** ECC[公钥base64字符串]公钥加密、私钥解密 ****************");
        PublicKey publicKey = EccKeyReadUtils.getPublicKeyByString(pubKeyBase64Str);
        log.info("ECC[公钥base64字符串]加密-明文：【{}】", plainText);
        String cipherText = EccUtils.encrypt(plainText, publicKey);
        log.info("ECC[公钥base64字符串]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = EccKeyReadUtils.getPrivateKeyByString(priKeyBase64Str);
        log.info("ECC[私钥base64字符串]解密-密文：【{}】", cipherText);
        String plainText2 = EccUtils.decrypt(cipherText, privateKey);
        log.info("ECC[私钥base64字符串]解密-明文：【{}】", plainText2);
        Assert.assertEquals(plainText, plainText2);

        /* 公私钥base64字符串加解密-私钥加密、公钥解密 */
        log.info("**************** ECC[公钥base64字符串]私钥加密、公钥解密 ****************");
        privateKey = EccKeyReadUtils.getPrivateKeyByString(priKeyBase64Str);
        log.info("ECC[私钥base64字符串]加密-明文：【{}】", plainText);
        cipherText = EccUtils.encrypt(plainText, privateKey);
        log.info("ECC[私钥base64字符串]加密-密文：【{}】", cipherText);

        publicKey = EccKeyReadUtils.getPublicKeyByString(pubKeyBase64Str);
        log.info("ECC[公钥base64字符串]解密-密文：【{}】", cipherText);
        String plainText3 = EccUtils.decrypt(cipherText, publicKey);
        log.info("ECC[公钥base64字符串]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }
}
