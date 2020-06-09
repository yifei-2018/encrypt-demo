package com.yifei.encrypt.algorithm.test.symmetric;

import com.yifei.encrypt.algorithm.symmetric.AesUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * AES工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 16:21
 */
@Slf4j
public class AesUtilsTest {

    @Test
    public void encryptTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        String secret = TestConstant.AES_SECRET;
        log.info("AES加密-明文：【{}】", plainText);
        String cipherText = AesUtils.encrypt(plainText, secret);
        log.info("AES加密-密文：【{}】", cipherText);

        log.info("AES解密-密文：【{}】", cipherText);
        String plainText1 = AesUtils.decrypt(cipherText, secret);
        log.info("AES解密-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);
    }

    @Test
    public void encryptWithModelTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        String secret = TestConstant.AES_SECRET;
        log.info("AES加密(含工作/填充模式)-明文：【{}】", plainText);
        String cipherText = AesUtils.encryptWithModel(plainText, secret);
        log.info("AES加密(含工作/填充模式)-密文：【{}】", cipherText);

        log.info("AES解密(含工作/填充模式)-密文：【{}】", cipherText);
        String plainText1 = AesUtils.decryptWithModel(cipherText, secret);
        log.info("AES解密(含工作/填充模式)-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);
    }


}
