package com.lanjy.blog.web;

import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.User;
import com.lanjy.blog.service.BlogService;
import com.lanjy.blog.service.CommentService;
import com.lanjy.blog.service.TagService;
import com.lanjy.blog.service.TypeService;
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
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommenedBlogs",blogService.listRecommenedBlogTop(8));
        return "index";
    }



    /**
     * 前端导航栏搜索
     * @param pageable
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                @RequestParam String query, Pageable pageable, Model model){
        Page<Blog> blogs = blogService.listBlog(pageable,query);
        model.addAttribute("page",blogs);
        model.addAttribute("query",query);

        return "search";
    }

    /**
     * 查看博客详情
     * @param id
     * @param session
     * @param model
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/blog/{id}")
    public String getAndConventBlogContentById(@PathVariable("id") String id, HttpSession session, Model model) throws NotFoundException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("user",new User());
        }else {
            model.addAttribute("user",user);
        }
        model.addAttribute("blog",blogService.getAndConventBlogContent(new Long(id)));
        model.addAttribute("comments",commentService.ListCommentByBlogId(new Long(id)));
        return "blog";
    }







    /**
     * 关于我导航栏
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/aboutme")
    public String aboutme(@PageableDefault(size = 4,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
//        Page<Blog> blogs = blogService.listBlog(pageable);
//        long totalElements = blogs.getTotalElements();
//        model.addAttribute("page",blogService.listBlog(pageable));
//        model.addAttribute("types",typeService.listTypeTop(6));
//        model.addAttribute("tags",tagService.listTagTop(10));
//        model.addAttribute("recommenedBlogs",blogService.listRecommenedBlogTop(8));
        return "aboutme";
    }




}
