package com.lanjy.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/11
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class LanjyblogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LanjyblogApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        //为了打包springboot项目
        return builder.sources(this.getClass());
    }
}