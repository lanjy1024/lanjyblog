package com.lanjy.blog.vo;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.vo
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/31
 */

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlogQuery {

    private Long    typeId;

    private Long    tagId;

    /*** 博客标题 */
    private String  title;


    /***是否推荐  */
    private boolean recommend;

}
