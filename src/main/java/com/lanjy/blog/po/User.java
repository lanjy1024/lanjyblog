package com.lanjy.blog.po;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    /*** User id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    /*** 昵称 */
    @Column(name = "nickName")
    private String nickName;
    /*** 用户名 */
    private String username;
    /*** 密码 */
    private String password;
    /*** 邮箱 */
    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "邮箱不能为空")
    private String email;
    /*** 头像 */
    private String avatar;
    /*** 用户类型 0-普通用戶，1—系統管理員，2-超級管理員 */
    private Integer utype;
    /*** 创建时间 */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime")
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime")
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
