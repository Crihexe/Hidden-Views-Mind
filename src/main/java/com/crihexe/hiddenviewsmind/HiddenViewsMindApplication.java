package com.crihexe.hiddenviewsmind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class HiddenViewsMindApplication {

    public static void main(String[] args) {
        //SpringApplication.run(HiddenViewsMindApplication.class, args);
        SpringApplication springApplication = new SpringApplication(HiddenViewsMindApplication.class);
        springApplication.addListeners(new LoggingListener());
        springApplication.run(args);
    }

}
