package com.lanjy.blog.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.po
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_blog")
public class Blog {
    /*** 博客id */
    @Id
    @GeneratedValue
    private Long   id;
    /*** 博客标题 */
    private String   title;
    /*** 博客内容
     * @Lob 指定大数据类型 当第一次初始化数据库的时候才有效
     * 通常结合@Basic(fetch = FetchType.LAZY)使用
     * LAZY是懒加载
     */
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String   content;
    /** 博客描述 */
    private String   description;
    /*** 博客封面图片 */
    private String   firstPicture;
    /*** 博客id */
    private String   flag;
    /*** 博客浏览次数 */
    private Integer   views;
    /***赞赏功能开启  */
    private Boolean   appreciation;
    /***版权开启  */
    private Boolean   shareStatement;
    /***评论开启  */
    private Boolean   commentabled;
    /***是否推荐  */
    private Boolean   recommened;
    /*** 发布 */
    private Boolean   published;
    /*** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /*** 更新时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /*** 博客 对 博客分类：多对一 */
    @ManyToOne
    private Type   type;

    /*** 博客 对 博客标签：多对多 ;级联新增*/
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags = new ArrayList<>();

    /*** 博客对用户：多对一 ;级联新增*/
    @ManyToOne
    private User   user;

    /*** 博客 对 评论：一对多 ;*/
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    /** 这个字段是不存在数据库的,该值是页面传进来的tag的id，例如:"1,2,3,4"*/
    @Transient
    private String   tagIds;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", description='" + description + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", recommened=" + recommened +
                ", published=" + published +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
