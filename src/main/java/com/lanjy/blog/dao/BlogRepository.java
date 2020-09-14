package com.lanjy.blog.dao;

import com.lanjy.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/24
 */
public interface BlogRepository  extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {

    @Modifying
    @Transactional
    @Query("update Blog a set " +
            "a.title = case when :#{#blog.title} is null then a.title else :#{#blog.title} end," +
            "a.content = case when :#{#blog.content} is null then a.content else :#{#blog.content} end," +
            "a.description = case when :#{#blog.description} is null then a.description else :#{#blog.description} end," +
            "a.firstPicture = case when :#{#blog.firstPicture} is null then a.firstPicture else :#{#blog.firstPicture} end," +
            "a.flag = case when :#{#blog.flag} is null then a.flag else :#{#blog.flag} end," +
            "a.views = case when :#{#blog.views} is null then a.views else :#{#blog.views} end," +
            "a.appreciation = case when :#{#blog.appreciation} is null then a.appreciation else :#{#blog.appreciation} end," +
            "a.shareStatement = case when :#{#blog.shareStatement} is null then a.shareStatement else :#{#blog.shareStatement} end, " +
            "a.commentabled = case when :#{#blog.commentabled} is null then a.commentabled else :#{#blog.commentabled} end," +
            "a.recommened = case when :#{#blog.recommened} is null then a.recommened else :#{#blog.recommened} end," +
            "a.published = case when :#{#blog.published} is null then a.published else :#{#blog.published} end," +
            "a.createTime = case when :#{#blog.createTime} is null then a.createTime else :#{#blog.createTime} end," +
            "a.updateTime = case when :#{#blog.updateTime} is null then a.updateTime else :#{#blog.updateTime} end " +
            "where a.id = :#{#blog.id}")
    int update(@Param("blog") Blog blog);


    @Query("select b from Blog b where b.recommened = true and b.published = true")
    List<Blog> findTop(Pageable pageable);


    @Query("select b from Blog b where b.published = true")
    Page<Blog> findTopByPublished(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.description like ?1")
    Page<Blog> findByQuery(Pageable pageable,String query);

    @Modifying
    @Query("update  Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    //SELECT DATE_FORMAT(b.create_time,'%Y') AS YEAR FROM t_blog b GROUP BY YEAR ORDER BY YEAR DESC;
    @Query("select function('date_format',b.createTime,'%Y') as year from Blog b group by function('date_format',b.createTime,'%Y') order by year desc ")
    List<String> findGroupYear();



    //SELECT * FROM t_blog b WHERE DATE_FORMAT(b.create_time,'%Y') = '2020';
    @Query("select b from Blog b where function('date_format',b.createTime,'%Y') = ?1")
    List<Blog> findByYear(String year);


    /*@Query("SELECT function('date_format',a.createTime,'%Y') AS createTime," +
            " a.id," +
            " a.title," +
            " a.content," +
            " a.description," +
            " a.firstPicture," +
            " a.flag," +
            " a.views," +
            " a.appreciation," +
            " a.shareStatement," +
            " a.commentabled," +
            " a.recommened," +
            " a.published," +
            " a.updateTime" +
            " FROM Blog a WHERE a.user.id = ?1")
    List<Blog> findByUserIDGroupYear(long userId);*/
    @Query("SELECT b FROM Blog b WHERE b.published = 1 and  b.user.id = ?1")
    List<Blog> findByUserIDGroupYear(long userId);


    @Query("select count(b) from Blog  b WHERE b.published = 1 and   b.user.id = ?1")
    long countBlogByUserID(long userid);
}
