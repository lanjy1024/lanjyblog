package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.Type;
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
@RequestMapping("/admin/types")
public class TypeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TypeService typeService;


    @GetMapping
    public String typeList(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                       Pageable pageable,Model model){
        Page<Type> typePage = typeService.listType(pageable);
        model.addAttribute("page",typePage);
        return "admin/types";
    }

    @GetMapping("/input")
    public String input(Model model){
        return "admin/types-input";
    }

    @GetMapping("/update")
    public String update(Model model){
        return "admin/types-update";
    }

    @PostMapping
    public String post(@Valid Type type,Model model,RedirectAttributes attributes) throws NotFoundException {
        Type typeByName = typeService.getTypeByName(type.getName());
        if (null != typeByName && null == type.getId()) {
            model.addAttribute("error_message","不能添加或更新为重复的分类");
            return "admin/types-input";
        }
        if (null != typeByName && null != type.getId()) {
            model.addAttribute("error_message","不能添加或更新为重复的分类");
            return "admin/types-update";
        }
        if(null == type.getId()){
            Type saveType = typeService.saveType(type);
            if(saveType == null){
                attributes.addFlashAttribute("error_message","添加失败");
            }else {
                attributes.addFlashAttribute("message","添加成功");
            }
            return "redirect:/admin/types";
        }
        Type updateType = typeService.updateType(type.getId(), type);
        if(updateType == null){
            attributes.addFlashAttribute("error_message","更新失败");
        }else {
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";

    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable("id") String id,
                         RedirectAttributes attributes){
        try{
            Type type = typeService.getType(new Long(id));
            if (type != null) {
                attributes.addFlashAttribute("type",type);
            }else {
                attributes.addFlashAttribute("error_message","编辑失败:该分类不存在");
                return "redirect:/admin/types";
            }
        }catch (Exception e){
            attributes.addFlashAttribute("error_message","编辑失败:"+e.getMessage());
            return "redirect:/admin/types";
        }
        return "redirect:/admin/types/update";
    }


    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes attributes){
        typeService.deleteType(new Long(id));
        Type type = typeService.getType(new Long(id));
        if(type == null){
            attributes.addFlashAttribute("message","删除成功");
        }else {
            attributes.addFlashAttribute("error_message","删除失败");
        }
        return "redirect:/admin/types";
    }
}