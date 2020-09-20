package com.lanjy.blog.web;


import com.lanjy.blog.po.User;
import com.lanjy.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;


/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/archives")
public class ArchivesShowController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;

    @GetMapping
    public String archives( HttpSession session,Model model){
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("countBlog",blogService.countBlog());
        return "archives";
    }

    @RequestMapping(value="/user/{userid}" , method = RequestMethod.GET )
    public String archivesByUserId(@PathVariable("userid") long userid, HttpSession session, Model model){
        User user = (User) session.getAttribute("loginUser");
        if (user != null){
            user.setPassword(null);
            session.setAttribute("loginUser",user);
        }
        model.addAttribute("archiveMap",blogService.archiveBlogByUsreId(userid,model));
        model.addAttribute("countBlog",blogService.countBlogByUserID(userid));
        return "archives_user";
    }

}