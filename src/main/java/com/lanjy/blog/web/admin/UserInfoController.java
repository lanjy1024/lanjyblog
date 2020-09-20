package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.User;
import com.lanjy.blog.service.CommentService;
import com.lanjy.blog.service.UserService;
import com.lanjy.blog.util.FtpFileUtil;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：注册登录
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/userinfo")
public class UserInfoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${aliyun.server.ip}")
    private String ALIYUN_SERVER_IP;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String toUserinfo(HttpSession session,Model model){
        User user = (User) session.getAttribute("loginUser");
        if (user != null){
            user.setPassword(null);
            session.setAttribute("loginUser",user);
            model.addAttribute("loginUser",user);
        }
        return "admin/user-info";
    }

    @PostMapping("/avatar")
    @ResponseBody
    public Map<String ,String> avatar(MultipartFile file, HttpSession session, Model model) throws IOException {
        Map<String ,String> res = new HashMap<>();
        //code为500表示上传失败
        res.put("code","500");
        String originalFilename = file.getOriginalFilename();
        String suffic = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffic;
        InputStream inputStream = file.getInputStream();
        boolean uploadFile = FtpFileUtil.uploadFile(newFileName, inputStream);
        if (uploadFile){
            logger.info("头像上传成功,{}",newFileName);
            User newUser = null;
            String avatar = "http://"+ALIYUN_SERVER_IP+"/image/"+newFileName;
            User userInSession = (User) session.getAttribute("loginUser");
            model.addAttribute("loginUser",session.getAttribute("loginUser"));
            try {
                User user = userService.findUserById(String.valueOf(userInSession.getId()));
                user.setAvatar(avatar);
                newUser = userService.saveUser(user);
                session.setAttribute("loginUser",newUser);
                //code为200表示上传成功
                res.put("code","200");
                res.put("newFileName",avatar);
            } catch (NotFoundException e) {
                logger.error(e.getMessage());
                return res;
            }

            try {
                updateCommentAvatar(newUser);
            } catch (Exception e) {
                logger.error("更新评论表中的头像链接异常,{}",e.getMessage());
            }

        }
        return res;
    }

    /**
     * 更新评论表中的头像链接
     * @param newUser
     */
    private void updateCommentAvatar(User newUser) {
        logger.info("更新评论表中的头像链接");
        commentService.updateCommentAvatar(newUser.getAvatar(),newUser.getId());
    }

    @PostMapping("/user")
    public String signup(User user,
            HttpSession session,
            Model model) {
        try {
            User newUser = userService.saveUser(user);
            session.setAttribute("user",newUser);
            model.addAttribute("user",newUser);
            model.addAttribute("message","修改成功！");
        } catch (NotFoundException e) {
            logger.error("修改用户信息失败",e.getMessage());
            model.addAttribute("error_message","修改用户信息失败！");
        }
        return "admin/user-info";
    }
}