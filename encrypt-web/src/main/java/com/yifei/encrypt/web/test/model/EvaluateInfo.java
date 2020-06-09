package com.yifei.encrypt.web.test.model;

import lombok.Data;

/**
 * 评价信息
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/6 11:39
 */
@Data
public class EvaluateInfo extends SerializableBase {
    private static final long serialVersionUID = -7650339226749276116L;
    /**
     * 评语
     */
    private String evalMsg;
}
