package com.yifei.encrypt.web.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 应用启动类
 *
 * @author luqs
 * @version v1.0
 * @date 2020/5/24 20:51
 */
@SpringBootApplication
@Slf4j
public class EncryptApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(EncryptApplication.class);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String appName = environment.getProperty("spring.application.name");
        String port = environment.getProperty("server.port");
        String contextPath = environment.getProperty("server.servlet.context-path");
        log.info("服务【{}】启动成功！端口号：【{}】,contextPath：【{}】", appName, port, contextPath);
    }
}
