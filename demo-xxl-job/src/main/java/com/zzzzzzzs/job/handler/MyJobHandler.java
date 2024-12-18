package com.zzzzzzzs.job.handler;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

@Component
public class MyJobHandler {

    @XxlJob("mySimpleJobHandler") // 对应 XXL-JOB Admin 中的 JobHandler 名称
    public void executeSimpleJob() {
        System.out.println("Hello, XXL-JOB!");
    }
}