package com.zzzzzzzs.duckdb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.zzzzzzzs.duckdb.mapper")
@SpringBootApplication
public class SpringbootDuckdbApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDuckdbApplication.class, args);
    }

}
