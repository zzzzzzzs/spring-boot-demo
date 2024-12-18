package com.zzzzzzzs.job.conf;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfig {

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses("http://127.0.0.1:8080/xxl-job-admin"); // XXL-JOB Admin 地址
        xxlJobSpringExecutor.setAppname("my-springboot-job"); // 执行器名称
        xxlJobSpringExecutor.setAddress(null);
        xxlJobSpringExecutor.setIp(null);
        xxlJobSpringExecutor.setPort(9999); // 执行器端口
        xxlJobSpringExecutor.setLogPath("/data/applogs/xxl-job"); // 日志路径
        xxlJobSpringExecutor.setLogRetentionDays(30); // 日志保存时间
        return xxlJobSpringExecutor;
    }
}