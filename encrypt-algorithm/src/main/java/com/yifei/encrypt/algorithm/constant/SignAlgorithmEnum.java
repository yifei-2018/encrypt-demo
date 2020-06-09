package com.yifei.encrypt.algorithm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签名算法枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/1 17:21
 */
@AllArgsConstructor
public enum SignAlgorithmEnum {
    /**
     * MD5WithRSA
     */
    MD5_WITH_RSA("MD5WithRSA"),
    /**
     * SHA1WithRSA
     */
    SHA1_WITH_RSA("SHA1WithRSA"),
    /**
     * SHA256WithRSA
     */
    SHA256_WITH_RSA("SHA256WithRSA"),
    /**
     * SHA1withDSA
     */
    SHA1_WITH_DSA("SHA1withDSA"),
    /**
     * SHA256WithDSA
     */
    SHA256_WITH_DSA("SHA256WithDSA"),
    /**
     * SHA512withDSA
     */
    SHA512_WITH_DSA("SHA512withDSA");

    @Getter
    private String algorithm;
}
