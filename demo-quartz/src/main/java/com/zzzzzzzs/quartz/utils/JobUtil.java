package com.zzzzzzzs.quartz.utils;

import com.zzzzzzzs.quartz.entity.JobInfo;
import com.zzzzzzzs.quartz.job.MyJob;
import org.quartz.*;

public class JobUtil {
    /**
     * 创建定时任务
     *
     * @param scheduler 调度器
     * @param jobInfo   任务信息
     */
    public static void createJob(Scheduler scheduler, JobInfo jobInfo) {

        Class<? extends Job> jobClass = null;
        JobDetail jobDetail = null;
        Trigger trigger = null;
        String cronExpression = jobInfo.getCronExpression();
        try {
            jobClass = (Class<? extends Job>) Class.forName(jobInfo.getJobClass());
            jobDetail = JobBuilder.newJob(MyJob.class)
                    // 设置任务为持久化存储，意味着即使没有与之关联的触发器，任务定义也会保留在调度器中
                    .storeDurably()
                    // 为任务指定一个唯一标识
                    .withIdentity(jobInfo.getJobName())
                    // 为任务添加一段描述信息，方便后续查看任务相关信息时了解其功能或用途
                    //.withDescription("这是MyJob的描述")
                    // 向任务的JobDataMap中添加一个键值对，键为"jobCount"，值为1，JobDataMap可用于在任务执行过程中传递数据
                    .usingJobData("count", 1)
                    .build();
            trigger = TriggerBuilder.newTrigger()
                    // 为触发器指定一个唯一标识，包括名称（"trigger1"）和所属组名（"group1"），用于在调度器中区分不同的触发器
                    .withIdentity(jobInfo.getJobName()+"_trigger")
                    // 指定该触发器关联的JobDetail对象，也就是上述定义的jobDetail方法所构建的任务细节对象
                    .forJob(jobDetail)
                    // 使用CronScheduleBuilder按照给定的cron表达式来构建任务的执行计划，确定任务何时执行
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (ClassNotFoundException | SchedulerException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 暂停任务
     *
     * @param scheduler 调度器
     * @param jobName   任务名称
     */
    public static void pauseJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     * @param scheduler
     * @param jobName
     */
    public static void resumeJob(Scheduler scheduler, String jobName) {

        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     * @param scheduler
     * @param jobName
     */
    public static void deleteJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 立即执行一次任务
     * @param scheduler
     * @param jobName
     */
    public static void runJobOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改任务
     * @param scheduler
     * @param jobInfo
     */
    public static void modifyJob(Scheduler scheduler, JobInfo jobInfo) {
        //获取任务唯一标识
        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getJobName()+"_trigger");

        try {
            //获取任务的触发器
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //重新构建任务的触发器
            String newCronExpression = jobInfo.getCronExpression();
            CronTrigger newTrigger = oldTrigger.getTriggerBuilder()
                    .withSchedule(CronScheduleBuilder.cronSchedule(newCronExpression))
                    .build();
            //重新设置任务的触发器
            scheduler.rescheduleJob(triggerKey, newTrigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}