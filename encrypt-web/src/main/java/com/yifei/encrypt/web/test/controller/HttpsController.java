package com.yifei.encrypt.web.test.controller;

import com.yifei.encrypt.web.test.model.EvaluateInfo;
import com.yifei.encrypt.web.test.model.PersonInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https
 *
 * @author luqs
 * @version v1.0
 * @date 2020/6/4 00:36
 */
@RequestMapping("/https")
@RestController
@Slf4j
public class HttpsController {

    @PostMapping("/simulate")
    public EvaluateInfo simulate(@RequestBody PersonInfo personInfo) {
        EvaluateInfo evaluateInfo = new EvaluateInfo();
        evaluateInfo.setEvalMsg(personInfo.getName() + ",you are the best!Come on!!!");
        return evaluateInfo;
    }
}
