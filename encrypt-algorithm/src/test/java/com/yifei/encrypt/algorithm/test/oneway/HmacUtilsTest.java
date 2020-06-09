package com.yifei.encrypt.algorithm.test.oneway;

import com.yifei.encrypt.algorithm.constant.HmacAlgorithmEnum;
import com.yifei.encrypt.algorithm.oneway.HmacUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * HMAC工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 21:03
 */
@Slf4j
public class HmacUtilsTest {

    @Test
    public void encryptTest() {
        String algorithm = HmacAlgorithmEnum.SHA512.getAlgorithm();
        String secret = TestConstant.HMAC_SECRET;
        String plainText = TestConstant.PLAIN_TEXT;
        log.info("【{}】明文：【{}】", algorithm, plainText);
        String cipherText = HmacUtils.encrypt(algorithm, plainText, secret);
        log.info("【{}】密文：【{}】", algorithm, cipherText);
    }
}
