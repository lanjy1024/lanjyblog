package com.lanjy.blog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.aspect
 * @类描述：日志处理aop
 * @创建人：lanjy
 * @创建时间：2020/1/12
 */


@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.lanjy.blog.web.*.*.*.*(..))")
    public void log() {

    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequsetLog requsetLog = new RequsetLog(url,ip,classMethod,args);
        logger.info("-------------doBefore-------------requset:{}",requsetLog);
    }

//    @After("log()")
//    public void doAfter() {
//        logger.info("-------------doAfter-------------");
//    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Object result) {
        logger.info("-------------doAfterReturn-------------result:{}", result);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class RequsetLog {
        private String   url;
        private String   ip;
        private String   classMethod;
        private Object[] args;

    }
}
