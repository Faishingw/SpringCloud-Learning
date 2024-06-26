package com.didispace.alibaba.nacos.config.client;

import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

@EnableNacosConfig
@NacosPropertySource(name = "custom", dataId = "didispace", autoRefreshed = true, first = true, before = SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, after = SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @RequestScope
    @Component
    class AB {
        String a;

        public void abc(String a) {
            this.a = a;
        }
    }


    @RestController
    static class TestController1 {

        @Autowired
        private AB ab;

        @GetMapping("/test1")
        public String hello() {
            ab.abc("a");
            return ab.a;
        }

    }


    @RestController
    @RefreshScope
    static class TestController {

        @Value("${didispace.title:}")
        private String title;

        @GetMapping("/test")
        public String hello() {
            return title;
        }

    }

    /**
     * 多文件加载的例子使用的验证接口
     * <p>
     * blog: http://blog.didispace.com/spring-cloud-alibaba-nacos-config-3/
     */
    @RestController
    @RefreshScope
    static class Test2Controller {

        @Value("${didispace.title:}")
        private String title;
        @Value("${aaa:}")
        private String aaa;
        @Value("${bbb:}")
        private String bbb;

        @GetMapping("/test2")
        public String test2() {
            return title + ", " + aaa + ", " + bbb;
        }

    }

}