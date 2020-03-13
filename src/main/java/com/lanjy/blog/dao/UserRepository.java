package com.lanjy.blog.dao;

import com.lanjy.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.dao
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public interface UserRepository extends JpaRepository<User,Long>,JpaSpecificationExecutor<User> {

    User findByUsernameAndPassword(String username,String password);


    User findUserByUsername(String username);
}
