package com.ithxc.blogdemo.controller;

import com.ithxc.blogdemo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hxc
 * @create 2020-03-17 17:51
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model,@RequestParam(defaultValue = "1") int pageNum){
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("page", blogService.listBlog(pageNum, 1000));
        return "archives";
    }
}
