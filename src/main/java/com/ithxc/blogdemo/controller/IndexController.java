package com.ithxc.blogdemo.controller;

import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.TagNum;
import com.ithxc.blogdemo.service.BlogService;
import com.ithxc.blogdemo.service.TagService;
import com.ithxc.blogdemo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-08 2:56
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1") int pageNum) {
        model.addAttribute("page", blogService.listBlog(pageNum, 6));
        //依据类型的数量排序后列出前6条type
        model.addAttribute("types",typeService.listTypeTop().subList(0, 6));
        //依据tag的数量排序后列出前10条tag
        List<TagNum> tagNums = tagService.listTagTop();
        Tag tag=new Tag();
        List<Tag> tags=new ArrayList<>();
        for (TagNum tagnum : tagNums) {
            tag=tagService.getTag(tagnum.getTagId());
            tag.setNum(tagnum.getNum());
            tags.add(tag);
        }
        model.addAttribute("tags", tags.subList(0, 9));
        //推荐最新8条微博
        model.addAttribute("recommendBlogs", blogService.listBlog().subList(0, 8));
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query,Model model,
                         @RequestParam(defaultValue = "1") int pageNum){
        model.addAttribute("page", blogService.listBlog(query, pageNum,5));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id ,Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        model.addAttribute("newblogs", blogService.listBlog().subList(0, 3));
        return "_fragments :: newblogList";
    }
}
