package com.lanjy.blog.quartzjob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * @author：lanjy
 * @date：2020/6/5
 * @description：
 */
public class HelloQuartzJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println(new Date()+"====HelloQuartzJob===============");
    }
}