package com.lanjy.blog.web;

import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.User;
import com.lanjy.blog.service.BlogService;
import com.lanjy.blog.service.CommentService;
import com.lanjy.blog.service.TagService;
import com.lanjy.blog.service.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web
 * @类描述：前端博客的首页功能
 * @创建人：lanjy
 * @创建时间：2020/2/11
 */
@Api(description = "博客门户",value = "博客门户",tags = {"博客门户"})  //使用@Api来修饰类
@Controller
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    /**
     * 博客前端首页
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 4, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            user.setPassword(null);
            session.setAttribute("loginUser", user);
            model.addAttribute("loginUser", user);
        }
        /*else{
            session.setAttribute("loginUser",gerUser());
            model.addAttribute("loginUser", gerUser());
        }*/
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommenedBlogs", blogService.listRecommenedBlogTop(8));
        return "index";
    }

    private User gerUser(){
        User user = new User();
        user.setAvatar("/static/image/avatar/7.jpg");
        user.setNickName("百里守约");
        user.setUsername("blsy");
        user.setId(3L);
        return user;
    }
    /**
     * 前端导航栏搜索
     *
     * @param pageable
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4, sort = {"id"}, direction = Sort.Direction.DESC)
                         @RequestParam String query, Pageable pageable, Model model) {

        Page<Blog> blogs = blogService.listBlog(pageable, query);
        model.addAttribute("page", blogs);
        model.addAttribute("query", query);

        return "search";
    }

    /**
     * 查看博客详情
     *
     * @param id
     * @param session
     * @param model
     * @return
     * @throws NotFoundException
     */
    @ApiOperation(value = "通过博客Id来获取博客详情信息", notes = "RestFul风格，需要传博客Id")
    //使用ApiImplcitParam修饰接口参数
    @ApiImplicitParam(name = "id", value = "博客Id", required = true)
    @GetMapping("/blog/{id}")
    public String getAndConventBlogContentById(@PathVariable("id") String id, HttpSession session, Model model) throws NotFoundException {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            user.setPassword(null);
            model.addAttribute("loginUser", user);
        }else {
            model.addAttribute("loginUser", new User());
        }
        model.addAttribute("blog", blogService.getAndConventBlogContent(new Long(id)));
        model.addAttribute("comments", commentService.ListCommentByBlogId(new Long(id)));
        return "blog";
    }

}