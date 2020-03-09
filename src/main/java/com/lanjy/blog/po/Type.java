package com.lanjy.blog.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

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
@Table(name = "t_type")
public class Type {
    /*** 类型id */
    @Id
    @GeneratedValue
    private Long   id;
    /*** 类型 */
    @NotBlank(message = "分类名称不能为空")
    private String   name;


    /*** 类型对博客：一对多 ;作为关系维护端*/
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
