package com.yifei.encrypt.web.test.model;

import com.yifei.encrypt.web.test.constant.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应结果
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/3 23:46
 */
@Data
@AllArgsConstructor
public class SignResponse<T> extends SerializableBase {
    private static final long serialVersionUID = 591968243118883419L;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 签名
     */
    private String sign;
    /**
     * 时间戳（ms）
     */
    private Long timeStamp;
    /**
     * 数据
     */
    private T data;

    public SignResponse() {
        this(ResponseCodeEnum.SUCCESS);
    }

    public SignResponse(T data) {
        this(ResponseCodeEnum.SUCCESS);
        this.data = data;
    }

    public SignResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SignResponse(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.getCode();
        this.msg = responseCodeEnum.getMsg();
    }
}
