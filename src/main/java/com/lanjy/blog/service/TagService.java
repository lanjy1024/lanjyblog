package com.lanjy.blog.service;

import com.lanjy.blog.po.Tag;
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
public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Long id, Tag tag) throws NotFoundException;

    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTagTop(Integer size);
}
