package com.yifei.encrypt.algorithm.asymmetric;

import com.yifei.encrypt.algorithm.Base64Utils;
import com.yifei.encrypt.algorithm.constant.ShaAlgorithmEnum;
import com.yifei.encrypt.algorithm.oneway.ShaUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 签名工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/30 12:09
 */
@Slf4j
public class SignUtils {

    private SignUtils() {
    }

    /**
     * 签名
     *
     * @param signAlgorithm 签名算法
     * @param data          待签名数据
     * @param key           私钥
     * @return String 若签名异常，则返回null
     */
    public static String sign(String signAlgorithm, String data, PrivateKey key) {
        // 先进行hash运算，防止加签字符超长且省时
        String hashData = ShaUtils.encrypt(ShaAlgorithmEnum.SHA1.getAlgorithm(), data);
        if (StringUtils.isBlank(hashData)) {
            log.error("签名-hash运算异常，数据：【{}】", data);
            return null;
        }
        byte[] signBytes = sign(signAlgorithm, hashData.getBytes(StandardCharsets.UTF_8), key);
        // base64编码
        return Base64Utils.encodeString(signBytes);
    }

    /**
     * 验签
     *
     * @param signAlgorithm 签名算法
     * @param sign          签名
     * @param data          待签名数据
     * @param key           私钥
     * @return String 若验签异常，则返回null
     */
    public static boolean verify(String signAlgorithm, String sign, String data, PublicKey key) {
        // 先进行hash运算，防止加签字符超长且省时
        String hashData = ShaUtils.encrypt(ShaAlgorithmEnum.SHA1.getAlgorithm(), data);
        if (StringUtils.isBlank(hashData)) {
            log.error("验签-hash运算异常，数据：【{}】", data);
            return false;
        }
        // base64解码
        byte[] signBytes = Base64Utils.decode(sign);
        return verify(signAlgorithm, signBytes, hashData.getBytes(StandardCharsets.UTF_8), key);
    }

    /**
     * 签名
     *
     * @param signAlgorithm 签名算法
     * @param dataBytes     待签名字节
     * @param key           私钥
     * @return byte[] 若签名异常，则返回null
     */
    public static byte[] sign(String signAlgorithm, byte[] dataBytes, PrivateKey key) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initSign(key);
            signature.update(dataBytes);
            return signature.sign();
        } catch (Exception e) {
            log.error("算法：【{}】，签名出现异常：", signAlgorithm, e);
        }
        return null;
    }

    /**
     * 验签
     *
     * @param signAlgorithm 签名算法
     * @param signBytes     原签名字节数组
     * @param dataBytes     待签名字节数组
     * @param key           私钥
     * @return boolean 若验签异常，则返回false
     */
    public static boolean verify(String signAlgorithm, byte[] signBytes, byte[] dataBytes, PublicKey key) {
        try {
            Signature signature = Signature.getInstance(signAlgorithm);
            signature.initVerify(key);
            signature.update(dataBytes);
            return signature.verify(signBytes);
        } catch (Exception e) {
            log.error("算法：【{}】，验签出现异常：", signAlgorithm, e);
        }
        return false;
    }
}
