package com.lanjy.blog.service;

import com.lanjy.blog.dao.BlogRepository;
import com.lanjy.blog.po.Blog;
import com.lanjy.blog.po.User;
import com.lanjy.blog.util.DateUtils;
import com.lanjy.blog.vo.BlogQuery;
import javassist.NotFoundException;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.function.DoubleToIntFunction;
import java.util.stream.Collectors;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service.impl
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/24
 */
@Service
public class BlogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BlogRepository blogRepository;

    @Transactional
    public Blog saveBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    @Transactional
    public Blog getAndConventBlogContent(Long id) throws NotFoundException{
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(!blogOptional.isPresent()){
            throw new NotFoundException("该博客不存在");
        }
        Blog blog = blogOptional.get();
        blogRepository.updateViews(id);
        return blog;
        //将博客内容content的Markdown格式转化成HTML
        /*String content = blog.getContent();
        Blog conventBlog = new Blog();
        BeanUtils.copyProperties(blog,conventBlog);
        conventBlog.setContent(markdownToHtml(content));
        blogRepository.updateViews(id);
        return conventBlog;*/
    }

    private String markdownToHtml(String md){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    public Blog getBlog(Long id) {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(!blogOptional.isPresent()){
            return null;
        }
        return blogOptional.get();
    }

    /**
     * jpa的高级条件查询
     * @param pageable
     * @param blogQuery
     * @return
     */
    public Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blogQuery.getTitle()) && blogQuery.getTitle() != null) {
                    predicates.add(cb.like(root.get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if (blogQuery.getUserId() != null) {
                    predicates.add(cb.equal(root.get("user"),blogQuery.getUserId()));
                }
                if (blogQuery.getTypeId() != null) {
                    predicates.add(cb.equal(root.get("type"),blogQuery.getTypeId()));
                }
                if (blogQuery.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommened"),blogQuery.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return  cb.equal(join.get("id"), tagId);
            }
        },pageable);
    }

    public Page<Blog> listBlog(Pageable pageable, String query) {
        //将Markdown转化成HTML插件commonmark
        return blogRepository.findByQuery(pageable,"%"+query+"%");
    }

    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findTopByPublished(pageable);
    }

    @Transactional
    public Blog updateBlog(Long id, Blog blog) throws NotFoundException {
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(!blogOptional.isPresent()){
            throw new NotFoundException("该博客不存在");
        }
        Blog targetBlog = blogOptional.get();
        blog.setUpdateTime(new Date());
        //将blog的属性值复制给targetBlog，createTime属性值除外，
        String[] ignoreProperties = {"createTime","views"};
        BeanUtils.copyProperties(blog,targetBlog, ignoreProperties);
        return blogRepository.save(targetBlog);
    }

    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    public int update(Blog blog) {
        return blogRepository.update(blog);
    }

    public void updateBlogViews(Long id, Blog blog) throws NotFoundException{
        Optional<Blog> blogOptional = blogRepository.findById(id);
        if(!blogOptional.isPresent()){
            throw new NotFoundException("该博客不存在");
        }
        Blog targetBlog = blogOptional.get();
        blog.setUpdateTime(new Date());
        blog.setViews(targetBlog.getViews()+1);
        //将blog的属性值复制给targetBlog，createTime属性值除外，
        String[] ignoreProperties = {"createTime"};
        BeanUtils.copyProperties(blog,targetBlog, ignoreProperties);
        blogRepository.save(targetBlog);
    }

    public List<Blog> listRecommenedBlogTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    public Map<String, List<Blog>> archiveBlog() {
        //先获取所有年份List
        List<String> years = blogRepository.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for(String year :years){
            //根据年份获取博客
            List<Blog> list = blogRepository.findByYear(year);
            map.put(year,list);
        }
        return map;
    }

    public Long countBlog() {
        return blogRepository.count();
    }


    public Map<String, List<Blog>> archiveBlogByUsreId(long userid, Model model) {
        //先获取所有年份List
        User[] user = {null};
        Map<String, List<Blog>> map = new HashMap<>();
        List<Blog> list = blogRepository.findByUserIDGroupYear(userid);
        if (list == null || list.isEmpty()) {
            return map;
        }
        Optional.ofNullable(list).orElse(new ArrayList<>()).forEach((Blog blog) -> {
            if (user[0] == null && blog.getUser() != null) {
                user[0] = blog.getUser();
            }
            String formatyyyy = DateUtils.formatyyyy(blog.getCreateTime());
            blog.setCreateTimeYYYY(formatyyyy);
        });
        model.addAttribute("user",user[0]);
        map = list.stream().collect(Collectors.groupingBy(Blog::getCreateTimeYYYY));
        return map;
    }

    public long countBlogByUserID(long userid) {

        return blogRepository.countBlogByUserID(userid);
    }
}
