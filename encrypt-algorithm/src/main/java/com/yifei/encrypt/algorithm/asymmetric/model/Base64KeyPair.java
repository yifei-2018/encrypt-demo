package com.yifei.encrypt.algorithm.asymmetric.model;

import lombok.Data;

/**
 * base64编码公私钥对
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/3 10:20
 */
@Data
public class Base64KeyPair {
    /**
     * 算法
     */
    private String algorithm;
    /**
     * key长度
     */
    private int keySize;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;
}
