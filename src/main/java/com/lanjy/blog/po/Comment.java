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
@Entity
@Table(name = "t_comment")
public class Comment {
    /*** 评论id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    /*** 昵称 */
    @Column(name = "nickName")
    private String nickName;
    /*** 邮箱 */
    private String email;
    /*** 头像 */
    private String avatar;
    /*** 评论内容 */
    private String content;
    @Column(name = "adminComment")
    private boolean adminComment;

    /*** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;

    /*** 博客评论 对 博客：多对多 ;被维护关系*/
    @ManyToOne
    private Blog blog;

    /** 评论回复的对象 */
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> replyComments = new ArrayList<>();
    @ManyToOne
    private Comment parentComment;

    /** 这个字段是不存在数据库的,该值是页面传进来的userId*/
    @Transient
    private String   userId;

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", blog=" + blog +
                ", adminComment=" + adminComment +
                ", replyComment=" + replyComments +
                '}';
    }
}
