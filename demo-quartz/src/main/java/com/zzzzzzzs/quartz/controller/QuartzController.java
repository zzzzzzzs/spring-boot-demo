package com.zzzzzzzs.quartz.controller;

import com.zzzzzzzs.quartz.entity.JobInfo;
import com.zzzzzzzs.quartz.job.MyJob;
import com.zzzzzzzs.quartz.utils.JobUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Resource
    private Scheduler scheduler;

    private final String jobName = "myJob";

    /**
     * 添加定时任务
     */
    @RequestMapping("/create")
    public String createJob() {
        log.info("创建定时任务");
        JobInfo jobInfo = new JobInfo(jobName, MyJob.class.getName(), "*/3 * * * * ?");
        JobUtil.createJob(scheduler, jobInfo);
        return "任务创建成功";
    }

    /**
     * 暂停定时任务
     */
    @RequestMapping("/pause")
    public String pauseJob() {
        log.info("暂停定时任务");
        JobUtil.pauseJob(scheduler, jobName);
        return "任务暂停成功";
    }

    /**
     * 恢复定时任务
     */
    @RequestMapping("/resume")
    public String resumeJob() {
        log.info("恢复定时任务");
        JobUtil.resumeJob(scheduler, jobName);
        return "任务恢复成功";
    }

    /**
     * 删除定时任务
     */
    @RequestMapping("/delete")
    public String deleteJob() {
        log.info("删除定时任务");
        JobUtil.deleteJob(scheduler, jobName);
        return "任务删除成功";
    }
    /**
     * 立即执行一次定时任务
     */
    @RequestMapping("/runonce")
    public String runJobOnce() {
        log.info("立即执行一次定时任务");
        JobUtil.runJobOnce(scheduler, jobName);
        return "任务执行一次成功";
    }
    /**
     * 修改定时任务
     */
    @RequestMapping("/modify")
    public String modifyJob() {
        log.info("修改定时任务");
        JobInfo jobInfo = new JobInfo(jobName, MyJob.class.getName(), "*/5 * * * * ?");
        JobUtil.modifyJob(scheduler, jobInfo);
        return "任务修改成功";
    }
}
