package com.lanjy.blog.dao;

import com.lanjy.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/2/19
 */
public interface CommentRepository extends JpaRepository<Comment,Long>,JpaSpecificationExecutor<Comment> {


    List<Comment> findByBlogIdAndParentCommentNull(Long blogId,Sort sort);
}
