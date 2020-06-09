package com.yifei.encrypt.web.test.model;

import lombok.Data;

/**
 * 请求参数
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/4 00:21
 */
@Data
public class SignRequest<T> extends SerializableBase {
    private static final long serialVersionUID = -5614171575654594343L;
    /**
     * app key
     */
    private String appKey;
    /**
     * api方法
     */
    private String method;
    /**
     * 时间戳
     */
    private Long timestamp;
    /**
     * 签名
     */
    private String sign;
    /**
     * 业务参数
     */
    private T bizParam;
}
