package com.yifei.encrypt.web.test.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/6 11:48
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum {
    /**
     * 成功
     */
    SUCCESS(10000, "成功"),
    /**
     * 签名验证失败
     */
    SIGN_VERIFY_FAIL(40001, "签名验证失败"),
    /**
     * 系统错误
     */
    SYS_ERROR(99999, "系统错误");

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 信息
     */
    private String msg;
}
