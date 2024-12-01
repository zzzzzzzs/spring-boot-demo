package com.zzzzzzzs.duckdb.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DuckDBConfig {

    @Bean
    public CommandLineRunner init(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection();
                 Statement stmt = connection.createStatement()) {

                // 执行安装空间扩展的命令
                stmt.execute("INSTALL spatial;");

                // 加载空间扩展
                stmt.execute("LOAD spatial;");

                System.out.println("空间插件已安装且加载成功");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        };
    }
}