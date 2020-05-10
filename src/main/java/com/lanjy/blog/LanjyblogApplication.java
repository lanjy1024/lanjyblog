package com.lanjy.blog;

import com.lanjy.blog.interceptor.LoginInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

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
@EnableSwagger2
public class LanjyblogApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(LanjyblogApplication.class, args);
    }

    /**
     * 设置一个开关，生产版本为false，关闭swagger
     */
    @Value("${swagger.ebable}")
    private boolean ebable;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).
                enable(ebable).select().apis(RequestHandlerSelectors.basePackage("com.lanjy.blog.web")). //扫描包
                paths(PathSelectors.any()).build();
        //可以设置为扫描多个包
        /**
         * com.google.common.base.Predicate<RequestHandler> selector1 = RequestHandlerSelectors.basePackage("设置你要扫描的包路径");
         * com.google.common.base.Predicate<RequestHandler> selector2 = RequestHandlerSelectors.basePackage("设置你要扫描的包路径");
         * createRestApi这样写即可。
         * @Bean
         *     public Docket createRestApi(){
         *         return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).
         *         enable(ebable).select().
         *         apis(Predicates.or(selector1,selector2)).
         *         paths(PathSelectors.any()).build();
         *     }
         */
    }


    @SuppressWarnings("deprecation")
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("接口文档").
                description("服务端通用接口").version("1.0").build();
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
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").
                addResourceLocations("classpath:/META-INF/resources/").
                setCachePeriod(0);
        super.addResourceHandlers(registry);
    }

    /** 添加拦截器 **/
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/admin/**")
                .addPathPatterns("/userinfo")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/admin")
                .excludePathPatterns("/admin/signup")
                .excludePathPatterns("/lanblog")
                .excludePathPatterns("/admin/login");

        super.addInterceptors(registry);
    }


}