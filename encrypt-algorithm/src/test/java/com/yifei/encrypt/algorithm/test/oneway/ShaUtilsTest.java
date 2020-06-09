package com.yifei.encrypt.algorithm.test.oneway;

import com.yifei.encrypt.algorithm.constant.ShaAlgorithmEnum;
import com.yifei.encrypt.algorithm.oneway.ShaUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * SHA工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 16:21
 */
@Slf4j
public class ShaUtilsTest {

    @Test
    public void encryptTest() {
        String algorithm = ShaAlgorithmEnum.SHA256.getAlgorithm();
        String plainText = TestConstant.PLAIN_TEXT;
        log.info("【{}】明文：【{}】", algorithm, plainText);
        String cipherText = ShaUtils.encrypt(algorithm, plainText);
        log.info("【{}】密文：【{}】", algorithm, cipherText);
    }

    @Test
    public void encryptByDigestUtilsTest() {
        String algorithm = ShaAlgorithmEnum.SHA256.getAlgorithm();
        String plainText = TestConstant.PLAIN_TEXT;
        log.info("【{}】明文：【{}】", algorithm, plainText);
        String cipherText = ShaUtils.encryptByDigestUtils(algorithm, plainText);
        log.info("【{}】密文：【{}】", algorithm, cipherText);
    }

    @Test
    public void sha256HexTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        String cipherText = ShaUtils.sha256Hex(plainText);
        log.info("密文：【{}】", cipherText);

        File file = new File(TestConstant.FILE_PATH);
        try {
            String fileCipherText = ShaUtils.sha256Hex(new FileInputStream(file));
            log.info("文件密文：【{}】", fileCipherText);
        } catch (FileNotFoundException e) {
            log.error("加密文件不存在：", e);
        }
    }
}
