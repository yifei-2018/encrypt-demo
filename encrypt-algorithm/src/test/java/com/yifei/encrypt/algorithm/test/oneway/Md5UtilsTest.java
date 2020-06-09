package com.yifei.encrypt.algorithm.test.oneway;

import com.yifei.encrypt.algorithm.oneway.Md5Utils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * MD5工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 16:21
 */
@Slf4j
public class Md5UtilsTest {

    @Test
    public void encryptTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        log.info("明文：【{}】", plainText);
        String cipherText = Md5Utils.encrypt(plainText);
        log.info("密文：【{}】", cipherText);
    }

    @Test
    public void md5HexTest() {
        String plainText = TestConstant.PLAIN_TEXT;
        log.info("明文：【{}】", plainText);
        String cipherText = Md5Utils.md5Hex(plainText);
        log.info("密文：【{}】", cipherText);

        File file = new File(TestConstant.FILE_PATH);
        try {
            String fileCipherText = Md5Utils.md5Hex(new FileInputStream(file));
            log.info("文件密文：【{}】", fileCipherText);
        } catch (FileNotFoundException e) {
            log.error("加密文件不存在：", e);
        }
    }

}
