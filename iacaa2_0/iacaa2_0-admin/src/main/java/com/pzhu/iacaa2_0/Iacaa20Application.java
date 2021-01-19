package com.pzhu.iacaa2_0;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pzhu.iacaa2_0.**.mapper")
public class Iacaa20Application {
    public static void main(String[] args) {
        SpringApplication.run(Iacaa20Application.class, args);
    }
}
