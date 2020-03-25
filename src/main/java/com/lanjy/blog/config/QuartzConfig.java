package com.lanjy.blog.config;

import com.lanjy.blog.job.QuartzDemoJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.config
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/3/23
 */
//@Configuration
public class QuartzConfig {


    /*@Bean
    public JobDetail printTimeJobDetail(){
        return JobBuilder.newJob(QuartzDemoJob.class)//PrintTimeJob我们的业务类
                .withIdentity("QuartzDemoJob")//可以给该JobDetail起一个id
                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
                .usingJobData("msg", "Hello Quartz")//关联键值对
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
    }*/
    /*@Bean
    public Trigger printTimeJobTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
        return TriggerBuilder.newTrigger()
                .forJob(printTimeJobDetail())//关联上述的JobDetail
                .withIdentity("quartzTaskService")//给Trigger起个名字
                .withSchedule(cronScheduleBuilder)
                .build();
    }*/
}
