package com.lanjy.blog.service.impl;

import com.lanjy.blog.dao.TypeRepository;
import com.lanjy.blog.exception.PageNotFoundException;
import com.lanjy.blog.po.Type;
import com.lanjy.blog.service.TypeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service.impl
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Service
public class TypeServiceImpl implements TypeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    public Type getType(Long id) {
        Optional<Type> tagOptional = typeRepository.findById(id);
        if(!tagOptional.isPresent()){
            throw new PageNotFoundException("不存在该类型");
        }
        return tagOptional.get();
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type){
        Optional<Type> tagOptional = typeRepository.findById(id);
        if(!tagOptional.isPresent()){
            throw new PageNotFoundException("不存在该类型");
        }
        Type type1 = tagOptional.get();
        BeanUtils.copyProperties(type,type1);
        return typeRepository.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findTypeByName(name);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageRequest = PageRequest.of(0, size,sort);
        return typeRepository.findTop(pageRequest);
    }
}
