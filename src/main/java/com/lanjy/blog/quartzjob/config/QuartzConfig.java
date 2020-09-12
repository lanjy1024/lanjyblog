/*
package com.lanjy.blog.quartzjob.config;

import com.lanjy.blog.quartzjob.HelloQuartzJob;
import com.lanjy.blog.quartzjob.MyQuartzJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
*/

/**
 * @author：lanjy
 * @date：2020/6/5
 * @description：SpringBoot2.0集成Quartz添加配置类
 */
//@Configuration
//public class QuartzConfig {
//
//    @Bean
//    public JobDetail helloJobDetail(){
//        JobDetail jobDetail = JobBuilder.newJob(HelloQuartzJob.class)
//                .withIdentity("HelloQuartzJob","myJobGroup1")
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//    @Bean
//    public Trigger helloTrigger(){
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(helloJobDetail())
//                .withIdentity("HelloQuartzJob_Trigger1","HelloQuartzJob_TriggerGroup1")
//                .startNow()
//                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//                .withSchedule(CronScheduleBuilder.cronSchedule("* * 12 * * ?"))
//                .build();
//        return trigger;
//    }
//
//    @Bean
//    public JobDetail myJobDetail(){
//        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class)
//                .withIdentity("MyQuartzJob","myJobGroup1")
//                .storeDurably()
//                .build();
//        return jobDetail;
//    }
//    @Bean
//    public Trigger myTrigger(){
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(myJobDetail())
//                .withIdentity("MyQuartzJob_Trigger","MyQuartzJob_TriggerGroup")
//                .startNow()
//                //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//                .withSchedule(CronScheduleBuilder.cronSchedule("* * 12 * * ?"))
//                .build();
//        return trigger;
//    }
//}
