package com.lanjy.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.interceptor
 * @类描述：
 *
 * 由spring2.x中即可以继承WebMvcConfigurationSupport 也可以实现WebMvcConfigurer。
 * 但是老的继承WebMvcConfigurerAdapter已经过时了
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    /*@Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        // 注册Spring data jpa pageable的参数分解器
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //默认静态资源处理
        registry.addResourceHandler("/static*//**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/css/")
                .addResourceLocations("classpath:/iamge/")
                .addResourceLocations("classpath:/js/")
                .addResourceLocations("classpath:/lib/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin*//**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/lanblog")
                .excludePathPatterns("/admin/login");

        super.addInterceptors(registry);
    }*/
}
