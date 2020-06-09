package com.yifei.encrypt.algorithm.asymmetric;

import com.yifei.encrypt.algorithm.Base64Utils;
import com.yifei.encrypt.algorithm.asymmetric.model.Base64KeyPair;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * 公私钥生成工具类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/24 20:52
 */
@Slf4j
public class KeyGenerateUtils {

    private KeyGenerateUtils() {
    }

    /**
     * 生成公私钥文件
     * <br/>注：公私钥未进行base64编码
     *
     * @param algorithm          算法
     * @param keySize            key长度
     * @param publicKeyFilePath  公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @return boolean
     */
    public static boolean generateKeyFile(String algorithm, int keySize, String publicKeyFilePath, String privateKeyFilePath) {
        KeyPair keyPair;
        try {
            keyPair = generateKeyPair(algorithm, keySize);
        } catch (NoSuchAlgorithmException e) {
            log.error("公私钥对生成出现异常：", e);
            return false;
        }

        File pubKeyFile;
        File priKeyFile;
        try {
            pubKeyFile = createNewFile(publicKeyFilePath);
            priKeyFile = createNewFile(privateKeyFilePath);
        } catch (IOException e) {
            log.error("创建公私钥文件出现异常：", e);
            return false;
        }

        try (
                ObjectOutputStream pubKeyOutputStream = new ObjectOutputStream(new FileOutputStream(pubKeyFile));
                ObjectOutputStream priKeyOutputStream = new ObjectOutputStream(new FileOutputStream(priKeyFile));
        ) {
            // 公钥
            pubKeyOutputStream.writeObject(keyPair.getPublic());
            pubKeyOutputStream.flush();
            // 私钥
            priKeyOutputStream.writeObject(keyPair.getPrivate());
            priKeyOutputStream.flush();
            return true;
        } catch (IOException e) {
            log.error("公私钥文件生成出现异常：", e);
        }
        return false;
    }

    /**
     * 生成base64公私钥文件
     *
     * @param algorithm          算法
     * @param keySize            key长度
     * @param publicKeyFilePath  公钥文件路径
     * @param privateKeyFilePath 私钥文件路径
     * @return boolean
     */
    public static boolean generateBase64KeyFile(String algorithm, int keySize, String publicKeyFilePath, String privateKeyFilePath) {
        KeyPair keyPair;
        try {
            keyPair = generateKeyPair(algorithm, keySize);
        } catch (NoSuchAlgorithmException e) {
            log.error("公私钥对[base64]生成出现异常：", e);
            return false;
        }

        File pubKeyFile;
        File priKeyFile;
        try {
            pubKeyFile = createNewFile(publicKeyFilePath);
            priKeyFile = createNewFile(privateKeyFilePath);
        } catch (IOException e) {
            log.error("创建公私钥文件[base64]出现异常：", e);
            return false;
        }

        try (
                FileOutputStream pubKeyOutputStream = new FileOutputStream(pubKeyFile);
                FileOutputStream priKeyOutputStream = new FileOutputStream(priKeyFile);
        ) {
            // 公钥
            pubKeyOutputStream.write(Base64Utils.encode(keyPair.getPublic().getEncoded()));
            pubKeyOutputStream.flush();
            // 私钥
            priKeyOutputStream.write(Base64Utils.encode(keyPair.getPrivate().getEncoded()));
            priKeyOutputStream.flush();
            return true;
        } catch (IOException e) {
            log.error("公私钥文件[base64]生成出现异常：", e);
        }
        return false;
    }

    /**
     * 生成base64公私钥文件
     *
     * @param algorithm 算法
     * @param keySize   key长度
     * @return Base64KeyPair
     */
    public static Base64KeyPair generateBase64KeyPair(String algorithm, int keySize) {
        Base64KeyPair base64KeyPair = new Base64KeyPair();
        base64KeyPair.setAlgorithm(algorithm);
        base64KeyPair.setKeySize(keySize);

        KeyPair keyPair;
        try {
            keyPair = generateKeyPair(algorithm, keySize);
        } catch (NoSuchAlgorithmException e) {
            log.error("公私钥对[base64]生成出现异常：", e);
            return null;
        }

        // 公钥
        base64KeyPair.setPublicKey(Base64Utils.encodeString(keyPair.getPublic().getEncoded()));
        // 私钥
        base64KeyPair.setPrivateKey(Base64Utils.encodeString(keyPair.getPrivate().getEncoded()));
        return base64KeyPair;
    }

    /**
     * 生成公私钥对
     *
     * @param algorithm 算法
     * @param keySize   key长度
     * @return KeyPairInfo
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair generateKeyPair(String algorithm, int keySize) throws NoSuchAlgorithmException {
        // 初始化 KeyPairGenerator
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
        keyPairGen.initialize(keySize);
        // 通过 KeyPairGenerator 生成公私钥对
        return keyPairGen.generateKeyPair();
    }

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return File
     * @throws IOException
     */
    private static File createNewFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }

        // 创建父级目录
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 创建文件
        file.createNewFile();
        return file;
    }

}
