package com.lanjy.blog.handler;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.handler
 * @类描述：自定义错误拦截异常，返回特定异常页面
 * @ControllerAdvice会拦截所有的@Controller注解的类
 * @创建人：lanjy
 * @创建时间：2020/1/12
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
        logger.error("RequestURL:{},Exception:{}",request.getRequestURL(),e);
        //如果指定了异常状态，则抛出（自定义的异常有指定异常状态，也可以定义一个异常码，通过异常码来判断是否是自定义异常）
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }

}
