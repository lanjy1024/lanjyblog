package com.lanjy.blog.quartzjob;

import lombok.SneakyThrows;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author：lanjy
 * @date：2020/6/5
 * @description：
 */
public class MyQuartzJob extends QuartzJobBean {


    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println(new Date()+"====MyQuartzJob=====开始======" );
        Thread.sleep(2000);
        System.out.println(new Date()+"====MyQuartzJob===完成========");
    }
}