package com.lanjy.blog.web.admin;

import com.lanjy.blog.po.User;
import com.lanjy.blog.service.UserService;
import com.lanjy.blog.util.MD5Utils;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog.web.admin
 * @类描述：注册登录
 * @创建人：lanjy
 * @创建时间：2020/1/13
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @GetMapping
    public String loginPage(){
        logger.info("跳转到登录页面");
        return "admin/login";
    }

    @GetMapping("/signup")
    public String signup(){
        logger.info("跳转到登录页面");
        return "admin/signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                        @RequestParam String nickName,
                        @RequestParam String password,
                        HttpSession session,
                        Model model,
                        RedirectAttributes attributes) throws NotFoundException {
        User userByUsername = userService.findUserByUsername(username);
        if (userByUsername != null){
            //错误提示，因为是重定向，所以使用RedirectAttributes，使用Model的话在重定向页面获取不到
            attributes.addFlashAttribute("message","用户名已存在");
            logger.info("用户名已存在");
            return "redirect:/admin/signup";
        }
        User user = new User();
        user.setUsername(username);
        user.setNickName(nickName);
        user.setPassword(MD5Utils.toMD5(password));
        user.setAvatar("http://47.112.197.101/image/1.jpg");
        user.setCreateTime(new Date());
        //user.setEmail(email);
        User user1 = userService.saveUser(user);
        session.setAttribute("loginUser",user1);
        model.addAttribute("loginUser",user1);
        logger.info("注册成功");
        return "admin/user-info";
    }

    @GetMapping("/index")
    public String index(HttpSession session,RedirectAttributes attributes){
        User user = (User) session.getAttribute("loginUser");
        if (user != null){
            user.setPassword(null);
            session.setAttribute("loginUser",user);
            logger.info("登录成功");
            return "admin/index";
        }
        //错误提示，因为是重定向，所以使用RedirectAttributes，使用Model的话在重定向页面获取不到
        attributes.addFlashAttribute("message","您已退出，请重新登录！");
        logger.info("用户名或密码错误");
        return "redirect:/admin";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username, password);
        if (user != null){
            user.setPassword(null);
            session.setAttribute("loginUser",user);
            logger.info("登录成功");
            return "admin/index";
        }
        //错误提示，因为是重定向，所以使用RedirectAttributes，使用Model的话在重定向页面获取不到
        attributes.addFlashAttribute("message","用户名或密码错误");
        logger.info("用户名或密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginUser");
        logger.info("注销成功");
        return "redirect:/admin";
    }


    @GetMapping("/pre/index")
    public String preIndex(HttpSession session){
        User user = (User) session.getAttribute("loginUser");
        if (user != null){
            user.setPassword(null);
            session.setAttribute("loginUser",user);
        }
        return "redirect:/";
    }
}