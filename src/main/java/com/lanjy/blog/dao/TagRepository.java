package com.lanjy.blog.dao;

import com.lanjy.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public interface TagRepository extends JpaRepository<Tag,Long>,JpaSpecificationExecutor<Tag> {

    Tag findTagByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
    @Query("select t from Tag t where t.id in ?1")
    List<Tag> findByIds(List<Long> longs);
}
