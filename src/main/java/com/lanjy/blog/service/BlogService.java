package com.lanjy.blog.service;

import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.Tag;
import com.lanjy.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/24
 */
public interface BlogService {

    Blog saveBlog(Blog blog);

    /**
     * 将博客内容content的Markdown格式转化成HTML
     * @param id
     * @return
     * @throws NotFoundException
     */
    Blog getAndConventBlogContent(Long id) throws NotFoundException;

    Blog getBlog(Long id) ;

    Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);

    /**
     *
     * @param pageable 分页查询
     * @param tagId 标签id
     * @return
     */
    Page<Blog> listBlog(Pageable pageable,Long tagId);

    /**
     * 前台首页的博客搜索
     * 搜索like博客标题或like博客描述
     * @param pageable
     * @param query
     * @return
     */
    Page<Blog> listBlog(Pageable pageable,String query);

    Page<Blog> listBlog(Pageable pageable);

    Blog updateBlog(Long id, Blog blog) throws NotFoundException;

    void deleteBlog(Long id);

    void updateBlogViews(Long id, Blog blog) throws NotFoundException;

    List<Blog> listRecommenedBlogTop(Integer size);

    /**
     * 归档
     * @return
     */
    Map<String,List<Blog>> archiveBlog();

    Long countBlog();
}
