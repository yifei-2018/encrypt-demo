package com.yifei.encrypt.algorithm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HMAC算法枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/1 16:56
 */
@AllArgsConstructor
public enum HmacAlgorithmEnum {
    /**
     * HMACD5
     */
    MD5("HMACD5"),
    /**
     * HMACSHA1
     */
    SHA1("HMACSHA1"),
    /**
     * HMACSHA256
     */
    SHA256("HMACSHA256"),
    /**
     * HMACSHA512
     */
    SHA512("HMACSHA512");

    @Getter
    private String algorithm;
}
