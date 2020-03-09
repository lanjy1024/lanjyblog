package com.lanjy.blog.web;

import com.lanjy.blog.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/lanblog")
public class DemoController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        String s = MD5Utils.toMD5("123456");
        System.out.println(s);
    }

    @GetMapping()
    public String index(){
        logger.info("-----------/index--------------");
        return "index";
    }

    @GetMapping("/types")
    public String typesInput(HttpSession session){
        logger.info("-----------/types--------------");
        return "admin/types";
    }
    @GetMapping("/types-input")
    public String types(HttpSession session){
        logger.info("-----------/types-input--------------");
        return "admin/types-input";
    }
}
