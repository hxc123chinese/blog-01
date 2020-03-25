package com.ithxc.blogdemo.service;

import com.ithxc.blogdemo.bean.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hxc
 * @create 2020-03-15 19:36
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
