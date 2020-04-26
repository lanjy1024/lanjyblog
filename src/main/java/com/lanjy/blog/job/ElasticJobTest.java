package com.lanjy.blog.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author：lanjy
 * @date：2020/4/13
 * @description：
 */
@Component
public class ElasticJobTest implements SimpleJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.info("============我是一个ElasticJob定时任务============");
    }
}
