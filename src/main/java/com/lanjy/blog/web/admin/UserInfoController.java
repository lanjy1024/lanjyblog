package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.User;
import com.lanjy.blog.service.UserService;
import com.lanjy.blog.util.FtpFileUtil;
import com.lanjy.blog.util.MD5Utils;
import javassist.NotFoundException;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @Autowired
    UserService userService;

    @GetMapping
    public String toUserinfo(HttpSession session,Model model){
        User user = (User) session.getAttribute("user");
        model.addAttribute("user",user);
        return "admin/user-info";
    }

    @PostMapping("/avatar")
    @ResponseBody
    public Map<String ,String> avatar(MultipartFile file, HttpSession session, Model model) throws IOException {
        Map<String ,String> res = new HashMap<>();
        String originalFilename = file.getOriginalFilename();
        String suffic = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffic;
        InputStream inputStream = file.getInputStream();
        boolean b = FtpFileUtil.uploadFile(newFileName, inputStream);
        model.addAttribute("user",session.getAttribute("user"));
        res.put("code","200");
        return res;
//        return "avatar :: originalFilename";
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