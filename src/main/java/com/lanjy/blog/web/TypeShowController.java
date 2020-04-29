package com.lanjy.blog.web;

import com.lanjy.blog.po.Type;
import com.lanjy.blog.service.BlogService;
import com.lanjy.blog.service.TypeService;
import com.lanjy.blog.vo.BlogQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/types")
public class TypeShowController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TypeService typeService;
    @Autowired
    private BlogService blogService;



    /**
     * 博客前端首页
     * @param id type的id
     * @param model
     * @param pageable
     * @return
     */
    @GetMapping("/{id}")
    public String index(@PathVariable("id") Long id, Model model,
            @PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable){
        List<Type> typeList = typeService.listTypeTop(1000000);
        if(0 == id && typeList.size() > 0){
            //从导航栏进来的
             id = typeList.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,blogQuery));
        model.addAttribute("types",typeList);
        model.addAttribute("activeTypeId",id);
        return "types";
    }


}