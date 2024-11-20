package com.zzzzzzzs.sqlite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.zzzzzzzs.sqlite.*.mapper*")
@SpringBootApplication
public class SpringbootSqliteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSqliteApplication.class, args);
    }

}
