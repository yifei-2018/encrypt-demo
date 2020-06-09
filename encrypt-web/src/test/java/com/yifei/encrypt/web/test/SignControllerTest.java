package com.yifei.encrypt.web.test;

import com.alibaba.fastjson.JSON;
import com.yifei.encrypt.algorithm.asymmetric.SignUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.constant.HmacAlgorithmEnum;
import com.yifei.encrypt.algorithm.constant.SignAlgorithmEnum;
import com.yifei.encrypt.algorithm.oneway.HmacUtils;
import com.yifei.encrypt.algorithm.oneway.Md5Utils;
import com.yifei.encrypt.web.test.constant.CmnConstant;
import com.yifei.encrypt.web.test.model.EvaluateInfo;
import com.yifei.encrypt.web.test.model.PersonInfo;
import com.yifei.encrypt.web.test.model.SignRequest;
import com.yifei.encrypt.web.test.model.SignResponse;
import com.yifei.encrypt.web.test.util.ApiSignUtils;
import com.yifei.encrypt.web.test.util.HttpUtils;
import com.yifei.encrypt.web.test.util.ResponseJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author luqs
 * @version v1.0
 * @date 2020/6/6 12:31
 */
@Slf4j
public class SignControllerTest {

    private SignRequest<PersonInfo> signRequest = new SignRequest<>();

    @Before
    public void init() {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName("毅飞");
        personInfo.setAge(28);
        personInfo.setSex(0);
        personInfo.setHobby("健身");
        personInfo.setMotto("奋斗超越极限，微笑面对未来！");

        signRequest.setAppKey("yfkj001");
        signRequest.setMethod("signTest");
        signRequest.setTimestamp(System.currentTimeMillis());
        signRequest.setBizParam(personInfo);
    }

    @Test
    public void verifyByMd5Test() {
        String secretField = "secret";
        String secret = "hdgsgjsgdgakddhaks";
        // 签名
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap, secretField, secret);
        String sign = Md5Utils.encrypt(signParamStr);
        signRequest.setSign(sign);
        log.info("md5验签参数值：【{}】", signRequest);
        String responseJson = HttpUtils.post(TestConstant.API_CTX_PATH + "/sign/verifyByMd5", JSON.toJSONString(signRequest));
        log.info("md5验签返回值：【{}】", responseJson);
        // 反序列
        SignResponse<EvaluateInfo> signResponse = ResponseJsonUtils.parseObject(responseJson, EvaluateInfo.class);
        if (signResponse == null) {
            log.error("md5验签响应反序列化失败！responseJson：【{}】", responseJson);
            Assert.fail("md5验签响应反序列化失败!responseJson：【" + responseJson + "】");
        }
        // 验签
        Map<String, Object> respParamMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String respSignParamStr = ApiSignUtils.appendParamStr(respParamMap, secretField, secret);
        String respSign = Md5Utils.encrypt(respSignParamStr);
        boolean verifyFlag = StringUtils.defaultString(signResponse.getSign()).equals(respSign);
        log.info("md5响应验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }

    @Test
    public void verifyByHmacTest() {
        String secret = "hcakhdekhadmshdkhad";
        // 签名
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = HmacUtils.encrypt(HmacAlgorithmEnum.SHA256.getAlgorithm(), signParamStr, secret);
        signRequest.setSign(sign);
        log.info("hmac验签参数值：【{}】", signRequest);
        String responseJson = HttpUtils.post(TestConstant.API_CTX_PATH + "/sign/verifyByHmac", JSON.toJSONString(signRequest));
        log.info("hmac验签返回值：【{}】", responseJson);
        // 反序列
        SignResponse<EvaluateInfo> signResponse = ResponseJsonUtils.parseObject(responseJson, EvaluateInfo.class);
        if (signResponse == null) {
            log.error("hmac验签响应反序列化失败！responseJson：【{}】", responseJson);
            Assert.fail("hmac验签响应反序列化失败!responseJson：【" + responseJson + "】");
        }
        // 验签
        Map<String, Object> respParamMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String respSignParamStr = ApiSignUtils.appendParamStr(respParamMap == null ? new HashMap<>(0) : respParamMap);
        String respSign = HmacUtils.encrypt(HmacAlgorithmEnum.SHA256.getAlgorithm(), respSignParamStr, secret);
        boolean verifyFlag = StringUtils.defaultString(signResponse.getSign()).equals(respSign);
        log.info("hmac响应验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }

    @Test
    public void verifyByRsaTest() {
        // 私钥
        String priKeyFilePath = "key/rsa_priKey_2.key";
        URL priKeyRrl = this.getClass().getClassLoader().getResource(priKeyFilePath);
        if (priKeyRrl == null) {
            log.error("rsa验签私钥文件【{}】不存在！", priKeyFilePath);
            Assert.fail("rsa验签私钥文件【" + priKeyFilePath + "】不存在！");
        }
        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKey(priKeyRrl.getPath());
        // 签名
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = SignUtils.sign(SignAlgorithmEnum.SHA256_WITH_RSA.getAlgorithm(), signParamStr, privateKey);
        signRequest.setSign(sign);
        log.info("rsa验签参数值：【{}】", signRequest);
        String responseJson = HttpUtils.post(TestConstant.API_CTX_PATH + "/sign/verifyByRsa", JSON.toJSONString(signRequest));
        log.info("rsa验签返回值：【{}】", responseJson);
        // 反序列
        SignResponse<EvaluateInfo> signResponse = ResponseJsonUtils.parseObject(responseJson, EvaluateInfo.class);
        if (signResponse == null) {
            log.error("rsa验签响应反序列化失败！responseJson：【{}】", responseJson);
            Assert.fail("rsa验签响应反序列化失败!responseJson：【" + responseJson + "】");
        }
        // 公钥
        String pubKeyFilePath = "key/rsa_pubKey_1.key";
        URL pubKeyRrl = this.getClass().getClassLoader().getResource(pubKeyFilePath);
        if (pubKeyRrl == null) {
            log.error("rsa验签公钥文件【{}】不存在！", pubKeyFilePath);
            Assert.fail("rsa验签公钥文件【" + pubKeyFilePath + "】不存在！");
        }
        PublicKey publicKey = RsaKeyReadUtils.getPublicKey(pubKeyRrl.getPath());
        // 验签
        Map<String, Object> respParamMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String respSignParamStr = ApiSignUtils.appendParamStr(respParamMap == null ? new HashMap<>(0) : respParamMap);
        boolean verifyFlag = SignUtils.verify(SignAlgorithmEnum.SHA256_WITH_RSA.getAlgorithm(), signResponse.getSign(), respSignParamStr, publicKey);
        log.info("rsa响应验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }

    @Test
    public void verifyByDsaTest() {
        // 私钥
        String priKeyFilePath = "key/dsa_priKey_2.key";
        URL priKeyRrl = this.getClass().getClassLoader().getResource(priKeyFilePath);
        if (priKeyRrl == null) {
            log.error("dsa验签私钥文件【{}】不存在！", priKeyFilePath);
            Assert.fail("dsa验签私钥文件【" + priKeyFilePath + "】不存在！");
        }
        PrivateKey privateKey = RsaKeyReadUtils.getPrivateKey(priKeyRrl.getPath());
        // 签名
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = SignUtils.sign(SignAlgorithmEnum.SHA256_WITH_DSA.getAlgorithm(), signParamStr, privateKey);
        signRequest.setSign(sign);
        log.info("dsa验签参数值：【{}】", signRequest);
        String responseJson = HttpUtils.post(TestConstant.API_CTX_PATH + "/sign/verifyByDsa", JSON.toJSONString(signRequest));
        log.info("dsa验签返回值：【{}】", responseJson);
        // 反序列
        SignResponse<EvaluateInfo> signResponse = ResponseJsonUtils.parseObject(responseJson, EvaluateInfo.class);
        if (signResponse == null) {
            log.error("dsa验签响应反序列化失败！responseJson：【{}】", responseJson);
            Assert.fail("dsa验签响应反序列化失败!responseJson：【" + responseJson + "】");
        }
        // 公钥
        String pubKeyFilePath = "key/dsa_pubKey_1.key";
        URL pubKeyRrl = this.getClass().getClassLoader().getResource(pubKeyFilePath);
        if (pubKeyRrl == null) {
            log.error("dsa验签公钥文件【{}】不存在！", pubKeyFilePath);
            Assert.fail("dsa验签公钥文件【" + pubKeyFilePath + "】不存在！");
        }
        PublicKey publicKey = RsaKeyReadUtils.getPublicKey(pubKeyRrl.getPath());
        // 验签
        Map<String, Object> respParamMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String respSignParamStr = ApiSignUtils.appendParamStr(respParamMap == null ? new HashMap<>(0) : respParamMap);
        boolean verifyFlag = SignUtils.verify(SignAlgorithmEnum.SHA256_WITH_DSA.getAlgorithm(), signResponse.getSign(), respSignParamStr, publicKey);
        log.info("dsa响应验签结果：【{}】", verifyFlag);
        Assert.assertTrue(verifyFlag);
    }
}
