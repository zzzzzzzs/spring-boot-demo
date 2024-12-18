package com.zzzzzzzs.quartz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobInfo {
    //任务名
    private String jobName;
    //任务类
    private String jobClass;
    //任务执行表达式
    private String cronExpression;
}
