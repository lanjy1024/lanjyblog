package com.lanjy.blog.service;

import com.lanjy.blog.po.Type;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    List<Type> listType();

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type) throws NotFoundException;

    void deleteType(Long id);

    Type getTypeByName(String name);

    List<Type> listTypeTop(Integer size);
}
