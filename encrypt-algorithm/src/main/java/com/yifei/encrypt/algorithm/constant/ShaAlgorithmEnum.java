package com.yifei.encrypt.algorithm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SHA算法枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/1 16:55
 */
@AllArgsConstructor
public enum ShaAlgorithmEnum {
    /**
     * SHA-1
     */
    SHA1("SHA-1"),
    /**
     * SHA-256
     */
    SHA256("SHA-256"),
    /**
     * SHA-384
     */
    SHA384("SHA-384"),
    /**
     * SHA-512
     */
    SHA512("SHA-512");

    @Getter
    private String algorithm;
}
