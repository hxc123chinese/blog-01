package com.ithxc.blogdemo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.ithxc.blogdemo.bean.Blog;
import com.ithxc.blogdemo.bean.BlogQuery;
import com.ithxc.blogdemo.bean.Tag;
import com.ithxc.blogdemo.bean.User;
import com.ithxc.blogdemo.service.BlogService;
import com.ithxc.blogdemo.service.TagService;
import com.ithxc.blogdemo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hxc
 * @create 2020-03-09 4:08
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String EDIT="admin/blogs-edit";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(defaultValue = "1") int pageNum, BlogQuery blogQuery){
        model.addAttribute("types", typeService.listType());
        PageInfo<Blog> blogPageInfo = blogService.listBlog(pageNum, 9, blogQuery);
        model.addAttribute("page", blogPageInfo);
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(Model model, @RequestParam(defaultValue = "1") int pageNum, BlogQuery blogQuery){
        PageInfo<Blog> blogPageInfo = blogService.listBlog(pageNum, 9, blogQuery);
        model.addAttribute("page", blogPageInfo);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return EDIT;
    }

    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id , Model model){
        setTypeAndTag(model);
        Blog blog=blogService.getBlog(id);
        blog.setId(id);
        blog.init();
        model.addAttribute("blog", blog);
        return EDIT;
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogService.addBlog(blog);
        if(b == null){
            attributes.addFlashAttribute("message","操作失败！");
        }else {
            attributes.addFlashAttribute("message","操作成功！");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id ,RedirectAttributes attributes ){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");

        return REDIRECT_LIST;
    }
}
