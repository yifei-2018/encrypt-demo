package com.yifei.encrypt.algorithm.test.asymmetric;

import com.yifei.encrypt.algorithm.asymmetric.SignUtils;
import com.yifei.encrypt.algorithm.asymmetric.dsa.DsaKeyReadUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.constant.SignAlgorithmEnum;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 签名工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/1 16:28
 */
@Slf4j
public class SignUtilsTest {
    @Before
    public void init() {
    }

    @Test
    public void signRsaTest() {
        // 公钥
        PublicKey publicKey = RsaKeyReadUtils.getPublicKey(RsaUtilsTest.PUB_KEY_FILE_PATH);
        // 私钥
        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKey(RsaUtilsTest.PRI_KEY_FILE_PATH);

        String data = TestConstant.PLAIN_TEXT;
        String signAlgorithm = SignAlgorithmEnum.SHA1_WITH_RSA.getAlgorithm();
        // 签名
        log.info("**************** RSA签名 ****************");
        String sign = SignUtils.sign(signAlgorithm, data, privateKey);
        log.info("待签名数据：【{}】", data);
        log.info("生成的签名：【{}】", sign);

        // 验签
        log.info("**************** RSA验签 ****************");
        boolean verifyFlag = SignUtils.verify(signAlgorithm, sign, data, publicKey);
        log.info("待签名数据：【{}】", data);
        log.info("签名：【{}】", sign);
        log.info("验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }

    @Test
    public void signDsaTest() {
        // 公钥
        PublicKey publicKey = DsaKeyReadUtils.getPublicKey(DsaUtilsTest.PUB_KEY_FILE_PATH);
        // 私钥
        PrivateKey privateKey = DsaKeyReadUtils.getPrivateKey(DsaUtilsTest.PRI_KEY_FILE_PATH);

        String data = TestConstant.PLAIN_TEXT;
        String signAlgorithm = SignAlgorithmEnum.SHA512_WITH_DSA.getAlgorithm();
        // 签名
        log.info("**************** DSA签名 ****************");
        String sign = SignUtils.sign(signAlgorithm, data, privateKey);
        log.info("待签名数据：【{}】", data);
        log.info("生成的签名：【{}】", sign);

        // 验签
        log.info("**************** DSA验签 ****************");
        boolean verifyFlag = SignUtils.verify(signAlgorithm, sign, data, publicKey);
        log.info("待签名数据：【{}】", data);
        log.info("签名：【{}】", sign);
        log.info("验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }

}
