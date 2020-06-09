package com.yifei.encrypt.algorithm.test;

import org.apache.commons.lang.RandomStringUtils;

import javax.swing.filechooser.FileSystemView;

/**
 * 测试常量类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/23 19:28
 */
public class TestConstant {
    private TestConstant() {
    }

    /**
     * 基础目录：系统桌面路径
     */
    public static final String BASE_DIR = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    /**
     * 明文
     */
    public static final String PLAIN_TEXT = "您好，世界！";
    /**
     * 加密文件路径
     */
    public static final String FILE_PATH = "C:\\Users\\卢青松\\Desktop\\微信截图_20200509144855.png";
    /**
     * hmac加密密钥
     */
    public static final String HMAC_SECRET = "123456";
    /**
     * des加密密钥（注：密钥至少8字节，建议8的倍数）
     */
    public static final String DES_SECRET = "12345678";
    /**
     * 3des加密密钥（注：密钥24字节）
     */
    public static final String TRIPLE_DES_SECRET = "123456781234567812345678";
    /**
     * aes加密密钥（注：密钥16、24和32位字节）
     */
    public static final String AES_SECRET = RandomStringUtils.random(16, true, true);

    /**
     * openssl.exe文件路径
     */
    public static final String OPENSSL_EXE = "D:\\developTool_install\\OpenSSL-Win64\\bin\\openssl.exe";
    /**
     * 公私钥文件目录
     */
    public static final String PEM_DIR = BASE_DIR + "\\https\\pem\\";
    /**
     * 证书目录
     */
    public static final String CERT_DIR = BASE_DIR + "\\https\\cert\\";

}
