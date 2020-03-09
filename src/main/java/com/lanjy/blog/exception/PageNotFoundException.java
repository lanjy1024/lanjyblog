package com.lanjy.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.exception
 * @类描述：自定义错误类，带状态码
 * @创建人：lanjy
 * @创建时间：2020/1/12
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PageNotFoundException extends RuntimeException{

    public PageNotFoundException() {
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
