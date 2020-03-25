package com.ithxc.blogdemo.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Collection;

/**
 * @author hxc
 * @create 2020-03-10 17:00
 */
@Controller
@RequestMapping("/admin")
public class TagContorller {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(Model model,@RequestParam(defaultValue = "1") int pageNum){
        PageInfo<Tag> tagsPageInfo = tagService.listTag(pageNum, 10);
        model.addAttribute("pages", tagsPageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String add(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 !=null){
            result.rejectValue("name", "nameError","该分类已存在，请勿重复添加！");
        }

        if(result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.addTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","添加失败！");
        }else {
            attributes.addFlashAttribute("message","添加成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String edit(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){

        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1 !=null){
            result.rejectValue("name", "nameError","该标签已存在，请勿重复添加！");
        }

        if(result.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/tags";
    }

}
