package com.ithxc.blogdemo.controller;

import com.ithxc.blogdemo.bean.BlogQuery;
import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.TagNum;
import com.ithxc.blogdemo.bean.Type;
import com.ithxc.blogdemo.service.BlogService;
import com.ithxc.blogdemo.service.TagService;
import com.ithxc.blogdemo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-17 13:30
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PathVariable Long id, Model model, @RequestParam(defaultValue = "1") int pageNum){
        List<TagNum> tagNums = tagService.listTagTop();
        Tag tag=new Tag();
        List<Tag> tags=new ArrayList<>();
        for (TagNum tagnum : tagNums) {
            tag=tagService.getTag(tagnum.getTagId());
            tag.setNum(tagnum.getNum());
            tags.add(tag);
        }
        if(id == -1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(pageNum, 6, id));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
