package com.lanjy.blog.service.impl;

import com.lanjy.blog.dao.CommentRepository;
import com.lanjy.blog.po.Comment;
import com.lanjy.blog.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service.impl
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/2/19
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentRepository commentRepository;

    //缓存迭代找出的所有子代集合
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> ListCommentByBlogId(Long blogId) {
        //按照创建时间倒序排列
        Sort sort = new Sort(Sort.Direction.ASC,"createTime");
        List<Comment> commentList = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(commentList);
    }

    /**
     * 处理子父级别的评论信息
     * @param commentList
     * @return
     */
    private List<Comment> eachComment(List<Comment> commentList) {
        //合并评论的各层子代到第一级子代集合中
        combiChildren(commentList);
        return commentList;
    }

    private void combiChildren(List<Comment> commentList) {
        for (Comment comment :commentList){
            List<Comment> replyList = comment.getReplyComments();
            for (Comment replyComment :replyList){
                //循环迭代，找出子代，存放到tempReplys中
                recursively(replyComment);
            }
            comment.setReplyComments(tempReplys);
            //清除临时缓存
            tempReplys = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     * @param comment
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0){
            List<Comment> replyComments = comment.getReplyComments();
            for(Comment reply : replyComments){
                tempReplys.add(reply);
                if(reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        Optional<Comment> optional = commentRepository.findById(parentCommentId);
        if (optional.isPresent()){
            comment.setParentComment(optional.get());
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void updateCommentAvatar(String avatar, String username, String nickName) {
        commentRepository.updateCommentAvatar(avatar,username,nickName);
    }


}
