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
@Table(name = "t_user")
public class User {
    /*** 评论id */
    @Id
    @GeneratedValue
    private Long   id;
    /*** 昵称 */
    private String nickName;
    /*** 用户名 */
    private String username;
    /*** 密码 */
    private String password;
    /*** 邮箱 */
    private String email;
    /*** 头像 */
    private String avatar;
    /*** 用户类型 */
    private Integer utype;
    /*** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;


    /*** 类型对博客：一对多 ;作为关系维护端*/
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", utype=" + utype +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
