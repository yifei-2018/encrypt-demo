package com.yifei.encrypt.web.test.model;

import lombok.Data;

/**
 * 个人信息
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/6 11:13
 */
@Data
public class PersonInfo extends SerializableBase {
    private static final long serialVersionUID = 5034819051005951638L;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 性别（0：男；1：女）
     */
    private int sex;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 格言
     */
    private String motto;
}
