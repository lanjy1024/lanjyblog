package com.lanjy.blog.service;

import com.lanjy.blog.po.Comment;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/2/19
 */
public interface CommentService {

    List<Comment> ListCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
