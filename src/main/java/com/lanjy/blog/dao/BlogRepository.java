package com.lanjy.blog.dao;

import com.lanjy.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/24
 */
public interface BlogRepository  extends JpaRepository<Blog,Long>,JpaSpecificationExecutor<Blog> {

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
}
