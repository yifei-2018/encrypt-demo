package com.yifei.encrypt.web.test.controller;

import com.yifei.encrypt.algorithm.asymmetric.SignUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.constant.HmacAlgorithmEnum;
import com.yifei.encrypt.algorithm.constant.SignAlgorithmEnum;
import com.yifei.encrypt.algorithm.oneway.HmacUtils;
import com.yifei.encrypt.algorithm.oneway.Md5Utils;
import com.yifei.encrypt.web.test.constant.CmnConstant;
import com.yifei.encrypt.web.test.constant.ResponseCodeEnum;
import com.yifei.encrypt.web.test.model.EvaluateInfo;
import com.yifei.encrypt.web.test.model.PersonInfo;
import com.yifei.encrypt.web.test.model.SignRequest;
import com.yifei.encrypt.web.test.model.SignResponse;
import com.yifei.encrypt.web.test.properties.InitProperties;
import com.yifei.encrypt.web.test.util.ApiSignUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 签名
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/3 23:31
 */
@RestController
@RequestMapping("/sign")
@Slf4j
public class SignController {
    /**
     * rsa公钥
     */
    private PublicKey rsaPubKey;
    /**
     * rsa私钥
     */
    private PrivateKey rsaPriKey;
    /**
     * dsa公钥
     */
    private PublicKey dsaPubKey;
    /**
     * dsa私钥
     */
    private PrivateKey dsaPriKey;

    @Resource
    InitProperties initProperties;

    /**
     * MD5验签
     *
     * @param signRequest 请求参数
     * @return ResponseResult<EvaluateInfo>
     */
    @PostMapping("/verifyByMd5")
    public SignResponse<EvaluateInfo> verifyByMd5(@RequestBody SignRequest<PersonInfo> signRequest) {
        if (signRequest == null) {
            return buildMd5Sign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数为空"));
        }
        if (StringUtils.isBlank(signRequest.getSign())) {
            return buildMd5Sign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数sign为空"));
        }

        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap, initProperties.getMd5SecretField(), initProperties.getMd5SecretValue());
        String sign = Md5Utils.encrypt(signParamStr);
        if (!signRequest.getSign().equals(sign)) {
            return buildMd5Sign(new SignResponse<>(ResponseCodeEnum.SIGN_VERIFY_FAIL));
        }

        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setEvalMsg("You are the best!");
        return buildMd5Sign(new SignResponse<>(evaluateInfo));
    }

    /**
     * HMAC验签
     *
     * @param signRequest 请求参数
     * @return ResponseResult<EvaluateInfo>
     */
    @PostMapping("/verifyByHmac")
    public SignResponse<EvaluateInfo> verifyByHmac(@RequestBody SignRequest<PersonInfo> signRequest) {
        if (signRequest == null) {
            return buildHmacSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数为空"));
        }
        if (StringUtils.isBlank(signRequest.getSign())) {
            return buildHmacSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数sign为空"));
        }

        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        if (paramMap == null || paramMap.size() < 1) {
            return buildHmacSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR));
        }
        String signParamStr = ApiSignUtils.appendParamStr(paramMap);
        String sign = HmacUtils.encrypt(HmacAlgorithmEnum.SHA256.getAlgorithm(), signParamStr, initProperties.getHmacSecret());
        if (!signRequest.getSign().equals(sign)) {
            return buildHmacSign(new SignResponse<>(ResponseCodeEnum.SIGN_VERIFY_FAIL));
        }

        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setEvalMsg("You are the best!");
        return buildHmacSign(new SignResponse<>(evaluateInfo));
    }

    /**
     * RSA验签
     *
     * @param signRequest 请求参数
     * @return ResponseResult<EvaluateInfo>
     */
    @PostMapping("/verifyByRsa")
    public SignResponse<EvaluateInfo> verifyByRsa(@RequestBody SignRequest<PersonInfo> signRequest) {
        if (signRequest == null) {
            return buildRsaSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数为空"));
        }
        if (StringUtils.isBlank(signRequest.getSign())) {
            return buildRsaSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数sign为空"));
        }

        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        boolean verifyFlag = SignUtils.verify(SignAlgorithmEnum.SHA256_WITH_RSA.getAlgorithm(), signRequest.getSign(), signParamStr, getRsaPubKey());
        if (!verifyFlag) {
            return buildRsaSign(new SignResponse<>(ResponseCodeEnum.SIGN_VERIFY_FAIL));
        }

        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setEvalMsg("You are the best!");
        return buildRsaSign(new SignResponse<>(evaluateInfo));
    }

    /**
     * DSA验签
     *
     * @param signRequest 请求参数
     * @return ResponseResult<EvaluateInfo>
     */
    @PostMapping("/verifyByDsa")
    public SignResponse<EvaluateInfo> verifyByDsa(@RequestBody SignRequest<PersonInfo> signRequest) {
        if (signRequest == null) {
            return buildDsaSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数为空"));
        }
        if (StringUtils.isBlank(signRequest.getSign())) {
            return buildDsaSign(new SignResponse<>(ResponseCodeEnum.SYS_ERROR.getCode(), "请求参数sign为空"));
        }

        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signRequest, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        boolean verifyFlag = SignUtils.verify(SignAlgorithmEnum.SHA256_WITH_DSA.getAlgorithm(), signRequest.getSign(), signParamStr, getDsaPubKey());
        if (!verifyFlag) {
            return buildDsaSign(new SignResponse<>(ResponseCodeEnum.SIGN_VERIFY_FAIL));
        }

        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setEvalMsg("You are the best!");
        return buildDsaSign(new SignResponse<>(evaluateInfo));
    }

    /**
     * 构建MD5签名
     *
     * @param signResponse 响应结果
     * @param <T>            泛型
     * @return ResponseResult
     */
    public <T> SignResponse<T> buildMd5Sign(SignResponse<T> signResponse) {
        signResponse.setTimeStamp(System.currentTimeMillis());
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap, initProperties.getMd5SecretField(), initProperties.getMd5SecretValue());
        String sign = Md5Utils.encrypt(signParamStr);
        signResponse.setSign(sign);
        return signResponse;
    }

    /**
     * 构建HMAC签名
     *
     * @param signResponse 响应结果
     * @param <T>            泛型
     * @return ResponseResult
     */
    public <T> SignResponse<T> buildHmacSign(SignResponse<T> signResponse) {
        signResponse.setTimeStamp(System.currentTimeMillis());
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = HmacUtils.encrypt(HmacAlgorithmEnum.SHA256.getAlgorithm(), signParamStr, initProperties.getHmacSecret());
        signResponse.setSign(sign);
        return signResponse;
    }

    /**
     * 获取rsa公钥
     *
     * @return PublicKey
     */
    public synchronized PublicKey getRsaPubKey() {
        if (rsaPubKey != null) {
            return rsaPubKey;
        }
        URL url = this.getClass().getClassLoader().getResource(initProperties.getRsaPubKeyFilePath());
        if (url == null) {
            log.warn("rsa公钥文件【{}】不存在！", initProperties.getRsaPubKeyFilePath());
            return null;
        }
        rsaPubKey = RsaKeyReadUtils.getPublicKey(url.getPath());
        return rsaPubKey;
    }

    /**
     * 获取ras私钥
     *
     * @return PrivateKey
     */
    public synchronized PrivateKey getRsaPriKey() {
        if (rsaPriKey != null) {
            return rsaPriKey;
        }
        URL url = this.getClass().getClassLoader().getResource(initProperties.getRsaPriKeyFilePath());
        if (url == null) {
            log.warn("rsa私钥文件【{}】不存在！", initProperties.getRsaPriKeyFilePath());
            return null;
        }
        rsaPriKey = RsaKeyReadUtils.getPrivateKey(url.getPath());
        return rsaPriKey;
    }

    /**
     * 构建rsa签名
     *
     * @param signResponse 响应结果
     * @param <T>            泛型
     * @return ResponseResult
     */
    public <T> SignResponse<T> buildRsaSign(SignResponse<T> signResponse) {
        signResponse.setTimeStamp(System.currentTimeMillis());
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = SignUtils.sign(SignAlgorithmEnum.SHA256_WITH_RSA.getAlgorithm(), signParamStr, getRsaPriKey());
        signResponse.setSign(sign);
        return signResponse;
    }

    /**
     * 获取dsa公钥
     *
     * @return PublicKey
     */
    public synchronized PublicKey getDsaPubKey() {
        if (dsaPubKey != null) {
            return dsaPubKey;
        }
        URL url = this.getClass().getClassLoader().getResource(initProperties.getDsaPubKeyFilePath());
        if (url == null) {
            log.warn("dsa公钥文件【{}】不存在！", initProperties.getDsaPubKeyFilePath());
            return null;
        }
        dsaPubKey = RsaKeyReadUtils.getPublicKey(url.getPath());
        return dsaPubKey;
    }

    /**
     * 获取ras私钥
     *
     * @return PrivateKey
     */
    public synchronized PrivateKey getDsaPriKey() {
        if (dsaPriKey != null) {
            return dsaPriKey;
        }
        URL url = this.getClass().getClassLoader().getResource(initProperties.getDsaPriKeyFilePath());
        if (url == null) {
            log.warn("dsa私钥文件【{}】不存在！", initProperties.getDsaPriKeyFilePath());
            return null;
        }
        dsaPriKey = RsaKeyReadUtils.getPrivateKey(url.getPath());
        return dsaPriKey;
    }

    /**
     * 构建dsa签名
     *
     * @param signResponse 响应结果
     * @param <T>            泛型
     * @return ResponseResult
     */
    public <T> SignResponse<T> buildDsaSign(SignResponse<T> signResponse) {
        signResponse.setTimeStamp(System.currentTimeMillis());
        Map<String, Object> paramMap = ApiSignUtils.getFieldMap(signResponse, CmnConstant.SIGN_IGNORE_FIELDS);
        String signParamStr = ApiSignUtils.appendParamStr(paramMap == null ? new HashMap<>(0) : paramMap);
        String sign = SignUtils.sign(SignAlgorithmEnum.SHA256_WITH_DSA.getAlgorithm(), signParamStr, getDsaPriKey());
        signResponse.setSign(sign);
        return signResponse;
    }

}
