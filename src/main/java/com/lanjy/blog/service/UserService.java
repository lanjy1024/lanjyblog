package com.lanjy.blog.service;

import com.lanjy.blog.po.User;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
public interface UserService {
    User checkUser(String userName,String password);


    User findUserById(String id);


}
