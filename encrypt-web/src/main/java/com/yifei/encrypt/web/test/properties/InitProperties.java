package com.yifei.encrypt.web.test.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * properties配置类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/5 14:33
 */
@Configuration("initProperties")
@PropertySource("classpath:init.properties")
@Data
public class InitProperties {
    /**
     * md5密钥字段
     */
    @Value("${md5.secret.field}")
    private String md5SecretField;
    /**
     * md5密钥
     */
    @Value("${md5.secret.value}")
    private String md5SecretValue;
    /**
     * hmac密钥
     */
    @Value("${hmac.secret}")
    private String hmacSecret;
    /**
     * rsa公钥文件路径
     */
    @Value("${rsa.public_key.file.path}")
    private String rsaPubKeyFilePath;
    /**
     * rsa私钥文件路径
     */
    @Value("${rsa.private_key.file.path}")
    private String rsaPriKeyFilePath;
    /**
     * dsa公钥文件路径
     */
    @Value("${dsa.public_key.file.path}")
    private String dsaPubKeyFilePath;
    /**
     * dsa私钥文件路径
     */
    @Value("${dsa.private_key.file.path}")
    private String dsaPriKeyFilePath;
}
