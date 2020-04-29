package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.Tag;
import com.lanjy.blog.service.TagService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/admin/tags")
public class TagController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TagService tagService;


    @GetMapping
    public String typeList(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                       Pageable pageable,Model model){
        Page<Tag> tagPage = tagService.listTag(pageable);
        model.addAttribute("page",tagPage);
        return "admin/tags";
    }

    @GetMapping("/input")
    public String input(){
        return "admin/tags-input";
    }

    @GetMapping("/update")
    public String update(){
        return "admin/tags-update";
    }

    @PostMapping
    public String post(@Valid Tag tag, Model model, RedirectAttributes attributes) throws NotFoundException {
        try {
            Tag getTagByName = tagService.getTagByName(tag.getName());
            if (null != getTagByName && null == tag.getId()) {
                model.addAttribute("error_message","不能添加或更新为重复的标签");
                return "admin/tags-input";
            }
            if (null != getTagByName && null != tag.getId()) {
                model.addAttribute("error_message","不能添加或更新为重复的标签");
                return "admin/tags-update";
            }
            if (null == tag.getId()) {
                tagService.saveTag(tag);
            }else {
                tagService.updateTag(tag.getId(),tag);
            }
            attributes.addFlashAttribute("message","添加成功");
        }catch (Exception e){
            attributes.addFlashAttribute("message","操作失败："+e.getMessage());
        }
        return "redirect:/admin/tags";
    }



    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") String id, RedirectAttributes attributes){
        try{
            Tag tag = tagService.getTag(new Long(id));
            if (tag != null) {
                attributes.addFlashAttribute("tag",tag);
            }else {
                attributes.addFlashAttribute("error_message","编辑失败:该标签不存在");
                return "redirect:/admin/tags";
            }
        }catch (Exception e){
            attributes.addFlashAttribute("error_message","编辑失败:"+e.getMessage());
            return "redirect:/admin/tags";
        }

        return "redirect:/admin/tags/update";
    }



    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes attributes){
        tagService.deleteTag(new Long(id));
        Tag tag = tagService.getTag(new Long(id));
        if(tag == null){
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("error_message","删除失败");
        }

        return "redirect:/admin/tags";
    }
}