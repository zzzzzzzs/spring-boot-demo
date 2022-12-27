package com.zzzzzzzs.helloworld;

import cn.hutool.core.util.StrUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.SqlDialect;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
/**
 * <p>
 * SpringBoot启动类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-09-28 14:49
 */
@SpringBootApplication
@RestController
public class SpringBootDemoHelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoHelloworldApplication.class, args);
    }

    /**
     * Hello，World
     *
     * @param who 参数，非必须
     * @return Hello, ${who}
     */
    @GetMapping("/hello")
    public String sayHello(@RequestParam(required = false, name = "who") String who) {
        if (StrUtil.isBlank(who)) {
            who = "World";
        }
        return StrUtil.format("Hello, {}!", who);
    }


    @GetMapping("/aaaa")
    public String aaaa() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 每隔1000 ms进行启动一个检查点【设置checkpoint的周期】
        env.setParallelism(1);

        EnvironmentSettings Settings = EnvironmentSettings.newInstance()
                .useBlinkPlanner()
                .inStreamingMode()
                .build();

        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env, Settings);
        tableEnv.getConfig().setSqlDialect(SqlDialect.DEFAULT);

        // 数据源表
        String sourceDDL =
                "CREATE TABLE mysql_binlog (\n" +
                        " user_id INT,\n" +
                        " user_name STRING,\n" +
                        " primary key (user_id) not enforced\n" +
                        ") WITH (\n" +
                        " 'connector' = 'mysql-cdc',\n" +
                        " 'hostname' = 'localhost',\n" +
                        " 'port' = '3366',\n" +
                        " 'username' = 'root',\n" +
                        " 'password' = '111',\n" +
                        " 'database-name' = 'test',\n" +
                        " 'table-name' = 'dim_user',\n" +
                        " 'scan.startup.mode' = 'initial'\n" +
                        ")";
        // 输出目标表
        String sinkDDL =
                "CREATE TABLE test_cdc_sink (\n" +
                        " user_id INT,\n" +
                        " user_name STRING,\n" +
                        " primary key (user_id) not enforced\n" +
                        ") WITH (\n" +
                        " 'connector' = 'jdbc',\n" +
                        " 'driver' = 'com.mysql.cj.jdbc.Driver',\n" +
                        " 'url' = 'jdbc:mysql://localhost:3366/test?serverTimezone=UTC&useSSL=false',\n" +
                        " 'username' = 'root',\n" +
                        " 'password' = '111',\n" +
                        " 'table-name' = 'dim_user_copy1'\n" +
                        ")";
        // 简单的聚合处理
        String transformDmlSQL = "insert into test_cdc_sink select * from mysql_binlog";

        tableEnv.executeSql(sourceDDL).print();
        tableEnv.executeSql(sinkDDL);
        tableEnv.executeSql(transformDmlSQL).print();

        env.execute("sync-flink-cdc");
        return "aaaa";
    }



}
