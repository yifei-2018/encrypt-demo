package com.yifei.encrypt.web.test;

import com.alibaba.fastjson.JSON;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaUtils;
import com.yifei.encrypt.algorithm.symmetric.AesUtils;
import com.yifei.encrypt.web.test.model.EncryptDto;
import com.yifei.encrypt.web.test.model.EvaluateInfo;
import com.yifei.encrypt.web.test.model.PersonInfo;
import com.yifei.encrypt.web.test.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.security.PublicKey;

/**
 * @author luqs
 * @version v1.0
 * @date 2020/6/7 11:31
 */
@Slf4j
public class EncryptControllerTest {

    private PersonInfo personInfo = new PersonInfo();

    @Before
    public void init() {
        personInfo.setName("毅飞");
        personInfo.setAge(28);
        personInfo.setSex(0);
        personInfo.setHobby("健身");
        personInfo.setMotto("奋斗超越极限，微笑面对未来！");
    }

    @Test
    public void simulateHttpsTest() {
        // 公钥
        String pubKeyFilePath = "key/rsa_pubKey_1.key";
        URL pubKeyRrl = this.getClass().getClassLoader().getResource(pubKeyFilePath);
        if (pubKeyRrl == null) {
            log.error("https加解密公钥文件【{}】不存在！", pubKeyFilePath);
            Assert.fail("https加解密公钥文件【" + pubKeyFilePath + "】不存在！");
        }
        PublicKey publicKey = RsaKeyReadUtils.getPublicKey(pubKeyRrl.getPath());
        // 加密
        String randomKey = RandomStringUtils.random(16, true, true);
        EncryptDto encryptDto = new EncryptDto();
        encryptDto.setKey(RsaUtils.encrypt(randomKey, publicKey));
        encryptDto.setValue(AesUtils.encrypt(JSON.toJSONString(personInfo), randomKey));
        log.info("https加解密模拟参数值：【{}】", encryptDto);
        String responseJson = HttpUtils.post(TestConstant.API_CTX_PATH + "/encrypt/httpsSimulate", JSON.toJSONString(encryptDto));
        log.info("https加解密模拟返回值：【{}】", responseJson);
        // 反序列
        EncryptDto respEncryptDto = JSON.parseObject(responseJson, EncryptDto.class);
        if (respEncryptDto == null) {
            log.error(" https加解密模拟响应反序列化失败！responseJson：【{}】", responseJson);
            Assert.fail(" https加解密模拟响应反序列化失败!responseJson：【" + responseJson + "】");
        }
        // 解密
        String secret = RsaUtils.decrypt(respEncryptDto.getKey(), publicKey);
        // 业务数据
        String bizData = AesUtils.decrypt(respEncryptDto.getValue(), secret);
        EvaluateInfo evaluateInfo = JSON.parseObject(bizData, EvaluateInfo.class);
        log.info("https加解密模拟-评价信息：【{}】", evaluateInfo);
        Assert.assertTrue(StringUtils.isNotBlank(bizData));
    }
}
