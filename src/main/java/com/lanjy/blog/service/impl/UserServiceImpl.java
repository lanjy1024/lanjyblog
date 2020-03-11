package com.lanjy.blog.service.impl;

import com.lanjy.blog.dao.UserRepository;
import com.lanjy.blog.po.User;
import com.lanjy.blog.service.UserService;
import com.lanjy.blog.util.MD5Utils;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.service.impl
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Service
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Override
    public User checkUser(String userName, String password) {
        return userRepository.findByUsernameAndPassword(userName, MD5Utils.toMD5(password));
    }

    @Override
    public User findUserById(String id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(new Long(id));
        if (userOptional.isPresent()) {
            throw new NotFoundException("该User不存在");
        }
        return userOptional.get();
    }
}
