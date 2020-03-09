package com.lanjy.blog.dao;

import com.lanjy.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public interface TypeRepository extends JpaRepository<Type,Long>{

    Type findTypeByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
