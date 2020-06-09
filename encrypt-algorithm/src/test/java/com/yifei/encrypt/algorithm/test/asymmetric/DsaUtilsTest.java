package com.yifei.encrypt.algorithm.test.asymmetric;

import com.yifei.encrypt.algorithm.asymmetric.dsa.DsaKeyGeneUtils;
import com.yifei.encrypt.algorithm.asymmetric.model.Base64KeyPair;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * DSA工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/29 00:03
 */
@Slf4j
public class DsaUtilsTest {
    /**
     * 公钥文件路径
     */
    public static final String PUB_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\dsa_pubKey_2.key";
    /**
     * 私钥文件路径
     */
    public static final String PRI_KEY_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\dsa_priKey_2.key";
    /**
     * 公钥[base64]文件路径
     */
    public static final String PUB_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\dsa_pubKey_base64.key";
    /**
     * 私钥[base64]文件路径
     */
    public static final String PRI_KEY_BASE64_FILE_PATH = TestConstant.BASE_DIR + "\\https\\key\\dsa_priKey_base64.key";

    @Before
    public void init() {
    }

    @Test
    public void generateKeyFileTest() {
        // 生成公私钥文件
        boolean genFlag = DsaKeyGeneUtils.generateKeyFile(PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);
        log.info("DSA公私钥文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genFlag, PUB_KEY_FILE_PATH, PRI_KEY_FILE_PATH);
        Assert.assertTrue(genFlag);
    }

    @Test
    public void encryptByBase64FileTest() {
        // 生成公私钥[base64]文件
        boolean genBase64Flag = DsaKeyGeneUtils.generateBase64KeyFile(PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);
        log.info("DSA公私钥[base64]文件生成结果：【{}】\n公钥路径：【{}】\n私钥路径：【{}】", genBase64Flag, PUB_KEY_BASE64_FILE_PATH, PRI_KEY_BASE64_FILE_PATH);
        Assert.assertTrue(genBase64Flag);
    }

    @Test
    public void encryptByBase64StringTest() {
        // 生成公私钥对[base64]
        Base64KeyPair keyPair = DsaKeyGeneUtils.generateBase64KeyPair();
        String pubKeyBase64Str = keyPair.getPublicKey();
        String priKeyBase64Str = keyPair.getPrivateKey();
        log.info("DSA公私钥[base64]字符串生成结果：【{}】\n公钥：【{}】\n私钥：【{}】", StringUtils.isNotBlank(pubKeyBase64Str), pubKeyBase64Str, priKeyBase64Str);
        Assert.assertTrue(StringUtils.isNotBlank(pubKeyBase64Str));
    }
}
