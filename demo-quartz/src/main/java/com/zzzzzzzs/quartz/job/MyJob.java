package com.zzzzzzzs.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
@Component
public class MyJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("定时任务执开始执行" + LocalDateTime.now());
        try {
            Thread.sleep(3000);
            log.info("定时任务执行结束" + LocalDateTime.now());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
