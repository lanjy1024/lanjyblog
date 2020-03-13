package com.lanjy.blog.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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
@Table(name = "t_tag")
public class Tag {
    /*** 标签id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    /*** 标签 */
    @NotBlank(message = "标签名称不能为空")
    @Column(name = "name")
    private String   name;

    /*** 博客标签 对 博客：多对多 ;被维护关系*/
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();


    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
