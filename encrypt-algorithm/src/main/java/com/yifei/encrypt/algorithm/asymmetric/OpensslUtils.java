package com.yifei.encrypt.algorithm.asymmetric;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * openssl工具类<br/>
 *
 * <strong>注：</strong>
 * <ol>
 *     <li>执行此工具类方法前，须先安装openSSL命令，安装步骤自行百度；</li>
 *     <li>工具类方法的命令行参数可自己根据实际需求进行修改，具体命令可参考https://www.cnblogs.com/aixiaoxiaoyu/p/8650180.html</li>
 * </ol>
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/30 15:24
 */
@Slf4j
public class OpensslUtils {
    /**
     * 成功码
     */
    private static final int PROCESS_CODE_SUCCESS = 0;
    /**
     * RSA私钥(pkcs#1)文件名
     */
    private static final String RSA_PKCS1_PRI_KEY_FILE_NAME = "pkcs#1_private_key.pem";
    /**
     * RSA公钥文件名
     */
    private static final String RSA_PUB_KEY_FILE_NAME = "public_key.pem";
    /**
     * RSA私钥(pkcs#8)文件名
     */
    private static final String RSA_PKCS8_PRI_KEY_FILE_NAME = "pkcs#8_private_key.pem";
    /**
     * CA证书私钥
     */
    private static final String CA_PRI_KEY_FILE_NAME = "ca_private_key.pem";
    /**
     * CA证书请求
     */
    private static final String CA_CSR_FILE_NAME = "ca.csr";
    /**
     * CA证书有效期（天）
     */
    private static final int CA_VALID_DAY = 365;
    /**
     * CA证书(.crt)
     */
    public static final String CA_CERT_CRT_FILE_NAME = "ca.crt";
    /**
     * CA证书(.pem)
     */
    public static final String CA_CERT_PEM_FILE_NAME = "ca.pem";
    /**
     * CA证书(.p12、.pfx)
     */
    public static final String CA_CERT_P12_FILE_NAME = "ca.p12";
    /**
     * CA证书(.p12、.pfx)密钥
     */
    public static final String CA_CERT_P12_FILE_PASSWORD = "test1234";

    private OpensslUtils() {
    }

    /**
     * 生成公私钥文件
     *
     * @param opensslExe openssl.exe路径
     * @param certDir    证书目录
     * @return boolean
     */
    public static boolean generateKeyFile(String opensslExe, String certDir) {
        // 创建目录
        mkDir(certDir);

        Runtime runtime = Runtime.getRuntime();
        try {
            // 校验 openssl 版本
            if (!checkOpensslVersion(runtime, opensslExe)) {
                return false;
            }

            /* 1.生成RSA私钥文件(PKCS#1) */
            // openssl genrsa -out pkcs#1_private_key.pem 2048
            String rsaGenCmd = opensslExe
                    + " genrsa -out " + certDir + RSA_PKCS1_PRI_KEY_FILE_NAME
                    + " 2048";
            log.info("1.生成RSA私钥文件(PKCS#1)命令行：【{}】", rsaGenCmd);
            if (!executeCmd(runtime, rsaGenCmd)) {
                log.error("生成RSA私钥文件(PKCS#1)出现异常！");
                return false;
            }

            /* 2.生成RSA公钥文件 */
            // openssl rsa -in private_key.pem -pubout -out public_key.pem
            String rsaCmd = opensslExe
                    + " rsa -in " + certDir + RSA_PKCS1_PRI_KEY_FILE_NAME
                    + " -pubout -out " + certDir + RSA_PUB_KEY_FILE_NAME;
            log.info("2.生成RSA公钥文件命令行：【{}】", rsaCmd);
            if (!executeCmd(runtime, rsaCmd)) {
                log.error("生成RSA公钥文件出现异常！");
                return false;
            }

            /* 3.转换RSA私钥格式为PKCS#8 */
            // openssl pkcs8 -topk8 -in rsa_private_key.pem -out pkcs8_rsa_private_key.pem -nocrypt
            String pkcs8Cmd = opensslExe
                    + " pkcs8 -inform PEM -in " + certDir + RSA_PKCS1_PRI_KEY_FILE_NAME
                    + " -topk8 -outform PEM -out " + certDir + RSA_PKCS8_PRI_KEY_FILE_NAME
                    + " -nocrypt";
            log.info("3.转换RSA私钥格式为PKCS#8命令行：【{}】", pkcs8Cmd);
            if (!executeCmd(runtime, pkcs8Cmd)) {
                log.error("转换RSA私钥格式为PKCS#8出现异常！");
                return false;
            }
            return true;
        } catch (IOException | InterruptedException e) {
            log.error("执行openssl命令出现异常：", e);
        }
        return false;
    }

    /**
     * 生成自签名证书
     *
     * @param opensslExe openssl.exe路径
     * @param certDir    证书目录
     * @return boolean
     */
    public static boolean generateCert(String opensslExe, String certDir) {
        // 创建目录
        mkDir(certDir);

        Runtime runtime = Runtime.getRuntime();
        try {
            // 校验 openssl 版本
            if (!checkOpensslVersion(runtime, opensslExe)) {
                return false;
            }

            /* 1.生成CA证书私钥文件 */
            // openssl genrsa -out pkcs#1_private_key.pem 2048
            String rsaGenCmd = opensslExe
                    + " genrsa -out " + certDir + CA_PRI_KEY_FILE_NAME
                    + " 1024";
            log.info("1.生成CA证书私钥文件命令行：【{}】", rsaGenCmd);
            if (!executeCmd(runtime, rsaGenCmd)) {
                log.error("生成CA证书私钥文件出现异常！命令行：【{}】", rsaGenCmd);
                return false;
            }

            /* 2.生成CSR(证书签名请求) */
            // openssl req -new -key server.key -out server.csr -subj /C=CN/ST=Guangdong/L=Guangzhou/O=xdevops/OU=xdevops/CN=gitlab.xdevops.cn
            String reqCmd = opensslExe
                    + " req -new -key " + certDir + CA_PRI_KEY_FILE_NAME
                    + " -out " + certDir + CA_CSR_FILE_NAME
                    + " -subj "
                    + " /C=CN/ST=BeiJing/L=BeiJing/O=yifei/OU=yifei/CN=www.yifei.cn";
            log.info("2.生成CSR(证书签名请求)命令行：【{}】", reqCmd);
            if (!executeCmd(runtime, reqCmd)) {
                log.error("生成CSR(证书签名请求)出现异常！命令行：【{}】", reqCmd);
                return false;
            }

            /* 3.自签署证书 */
            // openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt
            String x509Cmd = opensslExe
                    + " x509 -req -days " + CA_VALID_DAY
                    + " -in " + certDir + CA_CSR_FILE_NAME
                    + " -signkey " + certDir + CA_PRI_KEY_FILE_NAME
                    + " -out " + certDir + CA_CERT_CRT_FILE_NAME;
            log.info("3.自签署证书命令行：【{}】", x509Cmd);
            if (!executeCmd(runtime, x509Cmd)) {
                log.error("自签署证书出现异常！命令行：【{}】", x509Cmd);
                return false;
            }

            /* 4.将证书导出成浏览器支持的.p12格式 */
            // openssl pkcs12 -export -clcerts -in ca/ca-cert.pem -inkey ca/ca-key.pem -out ca/ca.p12 -password  pass:changeit
            String pkcs12Cmd = opensslExe
                    + " pkcs12 -export -clcerts -in " + certDir + CA_CERT_CRT_FILE_NAME
                    + " -inkey " + certDir + CA_PRI_KEY_FILE_NAME
                    + " -out " + certDir + CA_CERT_P12_FILE_NAME
                    + " -password  pass:" + CA_CERT_P12_FILE_PASSWORD;
            log.info("4.将证书导出成浏览器支持的.p12格式命令行：【{}】", pkcs12Cmd);
            if (!executeCmd(runtime, pkcs12Cmd)) {
                log.error("将证书导出成浏览器支持的.p12格式出现异常！命令行：【{}】", pkcs12Cmd);
                return false;
            }
            return true;
        } catch (IOException | InterruptedException e) {
            log.error("执行openssl命令出现异常：", e);
        }
        return false;
    }

    /**
     * 校验openssl版本
     *
     * @param runtime    运行期
     * @param opensslExe openssl程序路径
     * @return boolean
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean checkOpensslVersion(Runtime runtime, String opensslExe) throws IOException, InterruptedException {
        // 校验 openssl.exe 是否存在
        File opensslExeFile = new File(opensslExe);
        if (!opensslExeFile.exists()) {
            log.error("opensslExe【{}】文件不存在！", opensslExe);
            return false;
        }

        String versionCmd = opensslExe + " version -v";
        log.info("检测openssl版本命令行：【{}】", versionCmd);
        return executeCmd(runtime, versionCmd);
    }

    /**
     * 执行命令行
     *
     * @param runtime 运行期
     * @param cmd     命令行
     * @return boolean
     * @throws IOException
     * @throws InterruptedException
     */
    private static boolean executeCmd(Runtime runtime, String cmd) throws IOException, InterruptedException {
        Process process = runtime.exec(cmd);
        int pCode = process.waitFor();
        if (PROCESS_CODE_SUCCESS == pCode) {
            return true;
        }
        log.error("执行命令出现异常：【{}】", getErrorMessage(process));
        process.destroy();
        return false;
    }

    /**
     * 获取进程错误信息
     *
     * @param process 进程
     * @return String
     */
    private static String getErrorMessage(Process process) {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        ) {
            String line;
            StringBuilder strBuilder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                strBuilder.append(line).append("\n");
            }
            return strBuilder.toString();
        } catch (IOException e) {
            log.error("获取进程错误信息出现异常：", e);
        }
        return null;
    }

    /**
     * 创建文件目录
     *
     * @param dirPath 文件目录路径
     * @return File
     */
    private static File mkDir(String dirPath) {
        File file = new File(dirPath);
        if (file.exists()) {
            return file;
        }

        // 创建父级目录
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        // 创建文件
        file.mkdir();
        return file;
    }

}
