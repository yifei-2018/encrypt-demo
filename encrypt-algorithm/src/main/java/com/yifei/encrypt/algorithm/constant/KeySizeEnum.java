package com.yifei.encrypt.algorithm.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 密钥长度枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/2 17:53
 */
@AllArgsConstructor
public enum KeySizeEnum {
    /**
     * 256
     */
    K_256(256),
    /**
     * 512
     */
    K_512(512),
    /**
     * 1024
     */
    K_1024(1024),
    /**
     * 2048
     */
    K_2048(2048);

    @Getter
    private int size;
}
