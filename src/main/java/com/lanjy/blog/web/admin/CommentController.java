package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.Comment;
import com.lanjy.blog.po.User;
import com.lanjy.blog.service.BlogService;
import com.lanjy.blog.service.CommentService;
import com.lanjy.blog.service.UserService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web
 * @类描述：博客的评论功能
 * @创建人：lanjy
 * @创建时间：2020/2/11
 */
@Controller
public class CommentController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    /**
     * 获取博客评论信息
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId,Model model){
        /*User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("user",new User());
        }else {
            model.addAttribute("user",user);
        }
        model.addAttribute("blog",blogService.getAndConventBlogContent(blogId));*/
        model.addAttribute("comments",commentService.ListCommentByBlogId(blogId));
        return "blog :: containerList";
    }



    /**
     * 博客评论信息提交
     * @param comment
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/comments")
    public String postComments(Comment comment,
                           HttpSession session, Model model) throws NotFoundException {

//        comment.setAvatar(user.getAvatar());
//        comment.setEmail(user.getEmail());
        Long blogId = comment.getBlog().getId();
        Blog blog = blogService.getBlog(blogId);
        comment.setBlog(blog);
        User user = userService.findUserById(comment.getUserId());
        comment.setAdminComment(user.getId().equals(blog.getUser().getId()));
        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }



}
