package com.lanjy.blog.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.job
 * @类描述：
 * spring自带的定时任务，
 * springboot实现定时任务主要是依靠两个注解@Scheduled，
 * 还需要在启动类上加注解@EnableScheduling
 * @创建人：lanjy
 * @创建时间：2020/3/23
 */
@Component
public class TestJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 40 10 ? * *")
    public void job(){



        logger.info("job......"+ LocalDate.now());
    }

}
