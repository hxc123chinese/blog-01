package com.ithxc.blogdemo.controller;

import com.ithxc.blogdemo.bean.BlogQuery;
import com.ithxc.blogdemo.bean.Type;
import com.ithxc.blogdemo.service.BlogService;
import com.ithxc.blogdemo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-17 13:30
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, Model model, @RequestParam(defaultValue = "1") int pageNum){
        List<Type> types=typeService.listTypeTop();
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page",blogService.listBlog(pageNum, 6, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
