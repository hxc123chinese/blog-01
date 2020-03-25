package com.ithxc.blogdemo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Type;
import com.ithxc.blogdemo.service.TypeService;
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
 * @create 2020-03-10 0:47
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //查出所有Type到types页面
    @GetMapping("/types")
    public String types(Model model,@RequestParam(defaultValue = "1") int pageNum){
        PageInfo<Type> typePageInfo = typeService.listType(pageNum, 10);
        model.addAttribute("pages", typePageInfo);
        return "admin/types";
    }

    //跳转到新增页面
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //添加后跳回types页面
    @PostMapping("/types")
    public String add(@Valid Type type, BindingResult result,RedirectAttributes attributes){

        Type type1=typeService.getTypeByName(type.getName());
        if(type1 !=null){
            result.rejectValue("name", "nameError","该分类已存在，请勿重复添加！");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.addType(type);
        if(t == null){
            attributes.addFlashAttribute("message","添加失败！");
        }else {
            attributes.addFlashAttribute("message","添加成功！");
        }
            return "redirect:/admin/types";
    }

    //携带一个type到修改页面
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //在修改页面修改后跳回types页面
    @PostMapping("/types/{id}")
    public String edit(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){

        Type type1=typeService.getTypeByName(type.getName());
        if(type1 !=null){
            result.rejectValue("name", "nameError","该分类已存在，请勿重复添加！");
        }

        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            attributes.addFlashAttribute("message","更新失败！");
        }else {
            attributes.addFlashAttribute("message","更新成功！");
        }
        return "redirect:/admin/types";
    }

    //删除操作
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }
}
