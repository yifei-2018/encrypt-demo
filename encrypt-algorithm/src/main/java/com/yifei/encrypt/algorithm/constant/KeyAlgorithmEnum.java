package com.yifei.encrypt.algorithm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 公私钥算法枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/2 17:46
 */
@AllArgsConstructor
public enum KeyAlgorithmEnum {
    /**
     * RSA
     */
    RSA("RSA"),
    /**
     * DSA
     */
    DSA("DSA"),
    /**
     * ECC
     */
    ECC("EC");

    @Getter
    private String algorithm;
}
