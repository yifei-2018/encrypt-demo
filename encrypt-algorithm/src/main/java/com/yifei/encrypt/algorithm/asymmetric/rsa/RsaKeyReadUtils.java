package com.yifei.encrypt.algorithm.asymmetric.rsa;

import com.yifei.encrypt.algorithm.asymmetric.KeyReadUtils;
import com.yifei.encrypt.algorithm.constant.KeyAlgorithmEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

/**
 * RSA公私钥读取工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/3 15:07
 */
@Slf4j
public class RsaKeyReadUtils {
    private RsaKeyReadUtils() {
    }

    /**
     * 获取公钥
     * <br/>注：未进行base64解码
     *
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKey(String publicKeyFilePath) {
        return KeyReadUtils.getPublicKey(publicKeyFilePath);
    }

    /**
     * 获取私钥
     * <br/>注：未进行base64解码
     *
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKey(String privateKeyFilePath) {
        return KeyReadUtils.getPrivateKey(privateKeyFilePath);
    }

    /**
     * 获取公钥
     *
     * @param publicKeyFilePath 公钥文件路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKeyByPem(String publicKeyFilePath) {
        return KeyReadUtils.getPublicKeyByPem(KeyAlgorithmEnum.RSA.getAlgorithm(), publicKeyFilePath);
    }

    /**
     * 获取私钥
     *
     * @param privateKeyFilePath 私钥文件路径
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKeyByPem(String privateKeyFilePath) {
        return KeyReadUtils.getPrivateKeyByPem(KeyAlgorithmEnum.RSA.getAlgorithm(), privateKeyFilePath);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥base64编码字符串
     * @return PublicKey 若异常，则返回null
     */
    public static PublicKey getPublicKeyByString(String publicKey) {
        return KeyReadUtils.getPublicKeyByString(KeyAlgorithmEnum.RSA.getAlgorithm(), publicKey);
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥base64编码字符串
     * @return PrivateKey 若异常，则返回null
     */
    public static PrivateKey getPrivateKeyByString(String privateKey) {
        return KeyReadUtils.getPrivateKeyByString(KeyAlgorithmEnum.RSA.getAlgorithm(), privateKey);
    }

    /**
     * 获取公钥
     *
     * @param crtFilePath 公钥文件(.crt、cer)路径
     * @return PublicKey 若获取失败，则返回null
     */
    public static PublicKey getPublicKeyByCert(String crtFilePath) {
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(crtFilePath))
        ) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
            KeyFactory keyFactory = KeyFactory.getInstance(KeyAlgorithmEnum.RSA.getAlgorithm());
            return keyFactory.generatePublic(new X509EncodedKeySpec(x509Certificate.getPublicKey().getEncoded()));
        } catch (Exception e) {
            log.error("获取crt公钥【{}】出现异常：", crtFilePath, e);
        }
        return null;
    }

    /**
     * 获取私钥
     *
     * @param p12FilePath 私钥文件(.pfx、.p12)路径
     * @param password    私钥文件密码
     * @return PrivateKey 若获取失败，则返回null
     */
    public static PrivateKey getPrivateKeyByCert(String p12FilePath, String password) {
        try (
                FileInputStream fileInputStream = new FileInputStream(new File(p12FilePath));
        ) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fileInputStream, password.toCharArray());
            Enumeration<String> aliasEnum = keyStore.aliases();
            String keyAlias = null;
            if (aliasEnum.hasMoreElements()) {
                keyAlias = aliasEnum.nextElement();
            }
            return (PrivateKey) keyStore.getKey(keyAlias, password.toCharArray());
        } catch (Exception e) {
            log.error("获取p12私钥【{}|{}】出现异常：", p12FilePath, password, e);
        }
        return null;
    }
}
