package com.yifei.encrypt.web.test.model;

import lombok.Data;

/**
 * 加解密请求
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/7 10:12
 */
@Data
public class EncryptDto extends SerializableBase {
    private static final long serialVersionUID = -6152950281226891966L;
    /**
     * 密钥（通过非对称加密算法对对称加密算法的密钥进行加密所得的值）
     */
    private String key;
    /**
     * 业务参数值（通过对称算法加密业务参数所得的值）
     */
    private String value;
}
