package com.yifei.encrypt.algorithm.test.symmetric;

import com.yifei.encrypt.algorithm.symmetric.DesUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * DES工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 16:21
 */
@Slf4j
public class DesUtilsTest {

    @Test
    public void encryptTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        String secret = TestConstant.DES_SECRET;
        log.info("des加密-明文：【{}】", plainText);
        String cipherText = DesUtils.encrypt(plainText, secret);
        log.info("des加密-密文：【{}】", cipherText);

        log.info("des解密-密文：【{}】", cipherText);
        String plainText1 = DesUtils.decrypt(cipherText, secret);
        log.info("des解密-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);
    }

    @Test
    public void encryptWithModelTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        String secret = TestConstant.DES_SECRET;
        log.info("des加密（含工作/填充模式）-明文：【{}】", plainText);
        String cipherText = DesUtils.encryptWithModel(plainText, secret);
        log.info("des加密（含工作/填充模式）-密文：【{}】", cipherText);

        log.info("des解密（含工作/填充模式）-密文：【{}】", cipherText);
        String plainText1 = DesUtils.decryptWithModel(cipherText, secret);
        log.info("des解密（含工作/填充模式）-明文：【{}】", plainText1);
        Assert.assertEquals(plainText, plainText1);
    }

}
