package com.yifei.encrypt.algorithm.test.asymmetric;

import com.yifei.encrypt.algorithm.asymmetric.OpensslUtils;
import com.yifei.encrypt.algorithm.test.TestConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * openssl工具测试类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/30 19:30
 */
@Slf4j
public class OpensslUtilsTest {

    @Test
    public void generateKeyFileTest() {
        String opensslExe = TestConstant.OPENSSL_EXE;
        String certDir = TestConstant.PEM_DIR;
        boolean generateFlag = OpensslUtils.generateKeyFile(opensslExe, certDir);
        log.info("openssl公私钥文件生成结果：【{}】\nopenssl.exe路径：【{}】\n证书路径：【{}】", generateFlag, opensslExe, certDir);
        Assert.assertTrue(generateFlag);
    }

    @Test
    public void generateCertTest() {
        String opensslExe = TestConstant.OPENSSL_EXE;
        String certDir = TestConstant.CERT_DIR;
        boolean generateFlag = OpensslUtils.generateCert(opensslExe, certDir);
        log.info("自签名证书生成结果：【{}】\nopenssl.exe路径：【{}】\n证书路径：【{}】", generateFlag, opensslExe, certDir);
        Assert.assertTrue(generateFlag);
    }
}
