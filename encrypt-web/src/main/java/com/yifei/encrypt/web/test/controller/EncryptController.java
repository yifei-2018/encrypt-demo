package com.yifei.encrypt.web.test.controller;

import com.alibaba.fastjson.JSON;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaKeyReadUtils;
import com.yifei.encrypt.algorithm.asymmetric.rsa.RsaUtils;
import com.yifei.encrypt.algorithm.symmetric.AesUtils;
import com.yifei.encrypt.web.test.model.EncryptDto;
import com.yifei.encrypt.web.test.model.EvaluateInfo;
import com.yifei.encrypt.web.test.model.PersonInfo;
import com.yifei.encrypt.web.test.properties.InitProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.URL;
import java.security.PrivateKey;

/**
 * 加解密
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/4 00:34
 */
@RequestMapping("/encrypt")
@RestController
@Slf4j
public class EncryptController {
    /**
     * rsa私钥
     */
    private PrivateKey rsaPriKey;

    @Resource
    InitProperties initProperties;

    /**
     * 模拟https数据传输过程<br/>
     * <strong>说明：</strong>
     * <ol>
     *     <li>非对称加密算法加解密速度慢，不及对称加密算法，不适合加解密较长的字符串或文件；</li>
     *     <li>非对称加密算法利用公私钥进行加解密（公钥加密，只能私钥解密；私钥加密，只能公钥解密），而对称加密算法共用一个密钥进行加解密，泄露密钥的风险较高，所以相对来说非对称加密算法安全性较高；</li>
     * </ol>
     * <strong>https请求报文加解密传输过程：（注：非https完整细致的请求响应流程，详情流程可参考其它资料）</strong>
     * <ol>
     *     <li>客户端通过对称加密算法来加密业务传输数据；</li>
     *     <li>客户端通过非对称加密算法（公钥）来加密对称加密算法的密钥；</li>
     *     <li>通过网络传输被加密过的对称加密算法密钥和业务数据；</li>
     *     <li>服务端接收数据；</li>
     *     <li>服务端通过非对称加密算法（私钥）来解密对称加密算法的密钥；</li>
     *     <li>服务端通过解密出来的对称加密算法密钥来解密被加密的业务数据；</li>
     * </ol>
     * <strong>https响应报文加解密传输过程：（注：非https完整细致的请求响应流程，详情流程可参考其它资料）</strong>
     * <ol>
     *     <li>服务端通过对称加密算法来加密业务传输数据；</li>
     *     <li>服务端通过非对称加密算法（私钥）来加密对称加密算法的密钥；</li>
     *     <li>通过网络传输被加密过的对称加密算法密钥和业务数据；</li>
     *     <li>客户端端接收数据；</li>
     *     <li>客户端通过非对称加密算法（公钥）来解密对称加密算法的密钥；</li>
     *     <li>客户端通过解密出来的对称加密算法密钥来解密被加密的业务数据；</li>
     * </ol>
     *
     * @param encryptDto 加密请求
     * @return EncryptDto
     */
    @PostMapping("/httpsSimulate")
    public EncryptDto simulateHttps(@RequestBody EncryptDto encryptDto) {
        String randomStr = RandomStringUtils.random(16, true, true);
        PrivateKey privateKey = getRsaPriKey();
        EncryptDto respEncryptDto = new EncryptDto();
        respEncryptDto.setKey(RsaUtils.encrypt(randomStr, privateKey));
        EvaluateInfo evaluateInfo = new EvaluateInfo();
        if (encryptDto == null || StringUtils.isBlank(encryptDto.getKey()) || StringUtils.isBlank(encryptDto.getValue())) {
            evaluateInfo.setEvalMsg("个人信息未传");
            respEncryptDto.setValue(AesUtils.encrypt(JSON.toJSONString(evaluateInfo), randomStr));
            return respEncryptDto;
        }

        // 密钥
        String secret = RsaUtils.decrypt(encryptDto.getKey(), privateKey);
        // 业务数据
        String bizData = AesUtils.decrypt(encryptDto.getValue(), secret);
        PersonInfo personInfo = JSON.parseObject(bizData, PersonInfo.class);
        log.info("个人信息：【{}】", personInfo);

        evaluateInfo.setEvalMsg("You are the best!");
        respEncryptDto.setValue(AesUtils.encrypt(JSON.toJSONString(evaluateInfo), randomStr));
        return respEncryptDto;
    }

    /**
     * 获取ras私钥
     *
     * @return PrivateKey
     */
    public synchronized PrivateKey getRsaPriKey() {
        if (rsaPriKey != null) {
            return rsaPriKey;
        }
        URL url = this.getClass().getClassLoader().getResource(initProperties.getRsaPriKeyFilePath());
        if (url == null) {
            log.warn("https模拟-rsa私钥文件【{}】不存在！", initProperties.getRsaPriKeyFilePath());
            return null;
        }
        rsaPriKey = RsaKeyReadUtils.getPrivateKey(url.getPath());
        return rsaPriKey;
    }
}
