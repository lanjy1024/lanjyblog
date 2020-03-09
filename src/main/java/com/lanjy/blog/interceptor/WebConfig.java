package com.lanjy.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.interceptor
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/lanblog")
                .excludePathPatterns("/admin/login");
        super.addInterceptors(registry);
    }
}
