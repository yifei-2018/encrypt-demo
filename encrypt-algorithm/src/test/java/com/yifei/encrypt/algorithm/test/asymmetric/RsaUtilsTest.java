package com.yifei.encrypt.algorithm.test.asymmetric;

import com.yifei.encrypt.algorithm.asymmetric.OpensslUtils;
import com.yifei.encrypt.algorithm.asymmetric.model.Base64KeyPair;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyGeneUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/29 00:03
 */
@Slf4j
public class RsaUtilsTest {
    /**
     * 公钥文件路径
     */
    public static final String PUB_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\rsa_pubKey_2.key";
    /**
     * 私钥文件路径
     */
    public static final String PRI_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\rsa_priKey_2.key";
    /**
     * 公钥[base64]文件路径
     */
    public static final String PUB_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\rsa_pubKey_base64.key";
    /**
     * 私钥[base64]文件路径
     */
    public static final String PRI_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\rsa_priKey_base64.key";

    @Before
    public void init() {
    }

    @Test
    public void encryptTest() {
        // 生成公私钥文件
        boolean genFlag = RsaKeyGeneUtils.generateKeyFile(PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);
        log.info("RSA公私钥文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genFlag, PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥文件加解密-公钥加密、私钥解密 */
        log.info("**************** RSA[公钥]公钥加密、私钥解密 ****************");
        PublicKey publicKey = RsaKeyReadUtils.getPublicKey(PUB_KEY_FILE_PATH);
        log.info("RSA[公钥]加密-明文：【{}】", plainText);
        String cipherText = RsaUtils.encrypt(plainText, publicKey);
        log.info("RSA[公钥]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKey(PRI_KEY_FILE_PATH);
        log.info("RSA[私钥]解密-密文：【{}】", cipherText);
        String plainText1 = RsaUtils.decrypt(cipherText, privateKey);
        log.info("RSA[私钥]解密-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);

        /* 公私钥文件加解密-私钥加密、公钥解密 */
        log.info("**************** RSA[私钥]私钥加密、公钥解密 ****************");
        privateKey = RsaKeyReadUtils.getPrivateKey(PRI_KEY_FILE_PATH);
        log.info("RSA[私钥]加密-明文：【{}】", plainText);
        cipherText = RsaUtils.encrypt(plainText, privateKey);
        log.info("RSA[私钥]加密-密文：【{}】", cipherText);

        publicKey = RsaKeyReadUtils.getPublicKey(PUB_KEY_FILE_PATH);
        log.info("RSA[公钥]解密-密文：【{}】", cipherText);
        String plainText3 = RsaUtils.decrypt(cipherText, publicKey);
        log.info("RSA[公钥]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }

    @Test
    public void encryptByBase64FileTest() {
        // 生成公私钥[base64]文件
        boolean genBase64Flag = RsaKeyGeneUtils.generateBase64KeyFile(PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);
        log.info("RSA公私钥[base64]文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genBase64Flag, PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥base64文件加解密-公钥加密、私钥解密 */
        log.info("**************** RSA[公钥base64文件]公钥加密、私钥解密 ****************");
        PublicKey publicKey = RsaKeyReadUtils.getPublicKeyByPem(PUB_KEY_BASE64_FILE_PATH);
        log.info("RSA[公钥base64文件]加密-明文：【{}】", plainText);
        String cipherText = RsaUtils.encrypt(plainText, publicKey);
        log.info("RSA[公钥base64文件]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKeyByPem(PRI_KEY_BASE64_FILE_PATH);
        log.info("RSA[私钥base64文件]解密-密文：【{}】", cipherText);
        String plainText2 = RsaUtils.decrypt(cipherText, privateKey);
        log.info("RSA[私钥base64文件]解密-明文：【{}】", plainText2);
        Assert.assertEquals(plainText, plainText2);

        /* 公私钥base64文件加解密-私钥加密、公钥解密 */
        log.info("**************** RSA[私钥base64文件]私钥加密、公钥解密 ****************");
        privateKey = RsaKeyReadUtils.getPrivateKeyByPem(PRI_KEY_BASE64_FILE_PATH);
        log.info("RSA[私钥base64文件]加密-明文：【{}】", plainText);
        cipherText = RsaUtils.encrypt(plainText, privateKey);
        log.info("RSA[私钥base64文件]加密-密文：【{}】", cipherText);

        publicKey = RsaKeyReadUtils.getPublicKeyByPem(PUB_KEY_BASE64_FILE_PATH);
        log.info("RSA[公钥base64文件]解密-密文：【{}】", cipherText);
        String plainText3 = RsaUtils.decrypt(cipherText, publicKey);
        log.info("RSA[公钥base64文件]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }

    @Test
    public void encryptByBase64StringTest() {
        // 生成公私钥对[base64]
        Base64KeyPair keyPair = RsaKeyGeneUtils.generateBase64KeyPair();
        String pubKeyBase64Str = keyPair.getPublicKey();
        String priKeyBase64Str = keyPair.getPrivateKey();
        log.info("RSA公私钥[base64]字符串生成结果：【{}】\n公钥：【{}】\n私钥：【{}】", StringUtils.isNotBlank(pubKeyBase64Str), pubKeyBase64Str, priKeyBase64Str);

        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥base64字符串加解密-公钥加密、私钥解密 */
        log.info("**************** RSA[公钥base64字符串]公钥加密、私钥解密 ****************");
        PublicKey publicKey = RsaKeyReadUtils.getPublicKeyByString(pubKeyBase64Str);
        log.info("RSA[公钥base64字符串]加密-明文：【{}】", plainText);
        String cipherText = RsaUtils.encrypt(plainText, publicKey);
        log.info("RSA[公钥base64字符串]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKeyByString(priKeyBase64Str);
        log.info("RSA[私钥base64字符串]解密-密文：【{}】", cipherText);
        String plainText2 = RsaUtils.decrypt(cipherText, privateKey);
        log.info("RSA[私钥base64字符串]解密-明文：【{}】", plainText2);
        Assert.assertEquals(plainText, plainText2);

        /* 公私钥base64字符串加解密-私钥加密、公钥解密 */
        log.info("**************** RSA[公钥base64字符串]私钥加密、公钥解密 ****************");
        privateKey = RsaKeyReadUtils.getPrivateKeyByString(priKeyBase64Str);
        log.info("RSA[私钥base64字符串]加密-明文：【{}】", plainText);
        cipherText = RsaUtils.encrypt(plainText, privateKey);
        log.info("RSA[私钥base64字符串]加密-密文：【{}】", cipherText);

        publicKey = RsaKeyReadUtils.getPublicKeyByString(pubKeyBase64Str);
        log.info("RSA[公钥base64字符串]解密-密文：【{}】", cipherText);
        String plainText3 = RsaUtils.decrypt(cipherText, publicKey);
        log.info("RSA[公钥base64字符串]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }

    @Test
    public void encryptByCertTest() {
        // 生成自签名证书
        String opensslExe = TestConstant.OPENSSL_EXE;
        String certDir = TestConstant.CERT_DIR;
        boolean generateFlag = OpensslUtils.generateCert(opensslExe, certDir);
        log.info("自签名证书生成结果：【{}】\nopenssl.exe路径：【{}】\n证书路径：【{}】", generateFlag, opensslExe, certDir);

        String crtFilePath = certDir + OpensslUtils.CA_CERT_CRT_FILE_NAME;
        String p12FilePath = certDir + OpensslUtils.CA_CERT_P12_FILE_NAME;
        String p12FilePassword = OpensslUtils.CA_CERT_P12_FILE_PASSWORD;
        String plainText = TestConstant.PLAIN_TEXT;
        /* 公私钥base64文件加解密-公钥加密、私钥解密 */
        log.info("**************** RSA[公钥base64文件]公钥加密、私钥解密 ****************");
        PublicKey publicKey = RsaKeyReadUtils.getPublicKeyByCert(crtFilePath);
        log.info("RSA[公钥base64文件]加密-明文：【{}】", plainText);
        String cipherText = RsaUtils.encrypt(plainText, publicKey);
        log.info("RSA[公钥base64文件]加密-密文：【{}】", cipherText);

        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKeyByCert(p12FilePath, p12FilePassword);
        log.info("RSA[私钥base64文件]解密-密文：【{}】", cipherText);
        String plainText2 = RsaUtils.decrypt(cipherText, privateKey);
        log.info("RSA[私钥base64文件]解密-明文：【{}】", plainText2);
        Assert.assertEquals(plainText, plainText2);

        /* 公私钥base64文件加解密-私钥加密、公钥解密 */
        log.info("**************** RSA[私钥base64文件]私钥加密、公钥解密 ****************");
        privateKey = RsaKeyReadUtils.getPrivateKeyByCert(p12FilePath, p12FilePassword);
        log.info("RSA[私钥base64文件]加密-明文：【{}】", plainText);
        cipherText = RsaUtils.encrypt(plainText, privateKey);
        log.info("RSA[私钥base64文件]加密-密文：【{}】", cipherText);

        publicKey = RsaKeyReadUtils.getPublicKeyByCert(crtFilePath);
        log.info("RSA[公钥base64文件]解密-密文：【{}】", cipherText);
        String plainText3 = RsaUtils.decrypt(cipherText, publicKey);
        log.info("RSA[公钥base64文件]解密-明文：【{}】", plainText3);
        Assert.assertEquals(plainText, plainText3);
    }
}
