package com.lanjy.blog.service.impl;

import com.lanjy.blog.dao.TagRepository;
import com.lanjy.blog.exception.PageNotFoundException;
import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.Tag;
import com.lanjy.blog.service.TagService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
public class TagServiceImpl implements TagService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long id){
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if(!tagOptional.isPresent()){
            return null;
        }
        return tagOptional.get();
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        List<Long> list = conwerToList(ids);
        return tagRepository.findByIds(conwerToList(ids));
    }

    public List<Long> conwerToList(String args){
        List<Long> list = new ArrayList<>();
        if(!"".equals(args) && args != null){
            String[] array = args.split(",");
            for(int i = 0; i < array.length; i++){
                list.add(new Long(array[i]));
            }
        }
        return list;
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag){
        Optional<Tag> tagOptional = tagRepository.findById(id);
        if(!tagOptional.isPresent()){
            throw new PageNotFoundException("不存在该类型");
        }
        Tag one = tagOptional.get();
        BeanUtils.copyProperties(tag,one);
        return tagRepository.save(one);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageRequest = PageRequest.of(0, size,sort);
        return tagRepository.findTop(pageRequest);
    }
}
