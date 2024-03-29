package com.lanjy.blog;

import com.lanjy.blog.interceptor.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/11
 */
@Controller
@SpringBootApplication
@EnableScheduling
@ImportResource(locations={"classpath:jobs.xml"})
public class LanjyblogApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(LanjyblogApplication.class, args);
    }

    /**
     * 解决jpa延迟加载的问题 也就是no-session 异常
     * @return
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册Spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }
    /** 默认静态资源处理器 **/
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //默认静态资源处理
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /** 添加拦截器 **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .addPathPatterns("/userinfo")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/signup")
                .excludePathPatterns("/lanblog")
                .excludePathPatterns("/admin/login");

        super.addInterceptors(registry);
    }
}