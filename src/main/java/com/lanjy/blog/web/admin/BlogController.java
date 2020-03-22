package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.Tag;
import com.lanjy.blog.po.User;
import com.lanjy.blog.service.BlogService;
import com.lanjy.blog.service.TagService;
import com.lanjy.blog.service.TypeService;
import com.lanjy.blog.util.MyBeanUtils;
import com.lanjy.blog.vo.BlogQuery;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：后台管理页面的Controller
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String INPUT = "admin/blogs-input";
    private static final String UPDATE = "admin/blogs-update";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";
    private static final String REDIRECT_UPDATE = "redirect:/admin/blogs/update";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    /**
     * 后台管理页面的博客列表
     * 只能获取当前登录用户的博客
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @GetMapping
    public String blogs(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blogQuery,
                                    HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        blogQuery.setUserId(user.getId());
        Page<Blog> tagPage = blogService.listBlog(pageable,blogQuery);
        model.addAttribute("page",tagPage);
        model.addAttribute("types",typeService.listType());
        return LIST;
    }

    /**
     * 博客列表的查询功能
     * 只能获取当前登录用户的博客
     * @param pageable
     * @param blogQuery
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 2,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable,BlogQuery blogQuery,
                                HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        blogQuery.setUserId(user.getId());
        Page<Blog> tagPage = blogService.listBlog(pageable,blogQuery);
        model.addAttribute("page",tagPage);
        model.addAttribute("types",typeService.listType());
        return "admin/blogs :: blogList";
    }

    /**
     * 新增博客
     * @param model
     * @return
     */
    @GetMapping("/input")
    public String input(Model model){
        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return INPUT;
    }


    /**
     * 新增博客或者修改博客信息的提交功能
     * @param session
     * @param blog
     * @param attributes
     * @return
     * @throws NotFoundException
     */
    @PostMapping
    public String post(HttpSession session, Blog blog, RedirectAttributes attributes) throws NotFoundException {
       try{
           blog.setUser((User) session.getAttribute("user"));
           blog.setType(typeService.getType(blog.getType().getId()));
           blog.setTags(tagService.listTag(blog.getTagIds()));
           if (null != blog.getId()) {
               blogService.updateBlog(blog.getId(),blog);
           }else {
               blog.setCreateTime(new Date());
               blog.setViews(0);
               blogService.saveBlog(blog);
           }
           attributes.addFlashAttribute("message","操作成功");
       } catch (Exception e){
           attributes.addFlashAttribute("message","操作失败:"+e.getLocalizedMessage());
       }
        return REDIRECT_LIST;
    }

    /**
     * 修改博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") String id,
                         RedirectAttributes attributes){
        Blog blog = blogService.getBlog(new Long(id));
        if (blog == null) {
            attributes.addFlashAttribute("error_message","编辑失败:该博客不存在");
            return REDIRECT_LIST;
        }
        List<Tag> tags = blog.getTags();
        StringBuffer tagIds = new StringBuffer();
        for(int i = 0;i < tags.size();i++){
            tagIds.append(tags.get(i).getId()+",");
        }
        blog.setTagIds(tagIds.substring(0,tagIds.length()-1));
        attributes.addFlashAttribute("blog",blog);
        attributes.addFlashAttribute("types",typeService.listType());
        attributes.addFlashAttribute("tags",tagService.listTag());

        return REDIRECT_UPDATE;
    }

   @GetMapping("/update")
    public String update(){
        return UPDATE;
    }

    /**
     * 删除博客
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes attributes){
        try{
            blogService.deleteBlog(new Long(id));
            attributes.addFlashAttribute("message","删除成功!");
        } catch (Exception e){
            attributes.addFlashAttribute("error_message","删除失败："+e.getMessage());
        }


        return REDIRECT_LIST;
    }
}