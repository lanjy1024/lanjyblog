package com.lanjy.blog.service;

import com.lanjy.blog.dao.UserRepository;
import com.lanjy.blog.po.User;
import com.lanjy.blog.util.MD5Utils;
import com.lanjy.blog.util.StringUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
public class UserService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    public User checkUser(String userName, String password) {
        return userRepository.findByUsernameAndPassword(userName, MD5Utils.toMD5(password));
    }

    public User findUserById(String id) throws NotFoundException {
        Optional<User> userOptional = userRepository.findById(new Long(id));
        if (!userOptional.isPresent()) {
            throw new NotFoundException("该User不存在");
        }
        return userOptional.get();
    }

    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public User saveUser(User user) throws NotFoundException {
        if (user.getId() == null) {
            return userRepository.save(user);
        }
        if (StringUtil.isNotEmpty(user.getAvatar())){
            String avatar = user.getAvatar().substring(user.getAvatar().lastIndexOf("/static"));
            user.setAvatar(avatar);
        }
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (!userOptional.isPresent()) {
            throw new NotFoundException("该User不存在");
        }
        User updateUser = userOptional.get();
        String[] ignoreProperties = {"createTime","password","utype"};
        BeanUtils.copyProperties(user,updateUser, ignoreProperties);
        return userRepository.save(updateUser);
    }
}
