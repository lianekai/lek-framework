package com.lianekai.main;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author lianekai
 * @version: 1.0
 * @date 2022/02/27 15:13
 */
@SpringBootApplication(scanBasePackages = {"com.lianekai"})

public class FrameWorkServiceApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FrameWorkServiceApplication.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(args);
    }
}
