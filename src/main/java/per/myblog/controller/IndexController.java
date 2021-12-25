package per.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import per.myblog.service.SysUserService;
import per.myblog.service.UsersService;
import per.myblog.service.WxTokenService;
import per.myblog.utils.AjaxResult;
import per.myblog.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
@RequestMapping(value = "/index")
public class IndexController {

    private static final AjaxResult result = new AjaxResult();

    @Autowired
    SysUserService sysUserService;
    @Autowired
    UsersService usersService;
    @Autowired
    WxTokenService wxTokenService;

    @RequestMapping(value = "/test")
    public AjaxResult test(HttpServletRequest request){
//        emailUtils.send("chanpwork@163.com", "00000");
        return result.successData("Hello World!---" + request.getParameter("userName"));
    }

    @RequestMapping(value = "/login")
    public AjaxResult login(HttpServletRequest request){
//        return wxTokenService.getTokenByCode(request.getParameter("code"));
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        return usersService.login(loginName, password);
    }

    @RequestMapping(value = "/register")
    public AjaxResult register(HttpServletRequest request){
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(loginName)) {
            result.failMsg("用户名为空");
        }
        if (StringUtils.isEmpty(password)) {
            result.failMsg("密码为空");
        }
        return usersService.register(loginName, password);
    }


//    @RequestMapping(value = "/getToken")
//    public AjaxResult onLogin(HttpServletRequest request){
//        return wxTokenService.getTokenByCode(request.getParameter("code"));
//    }
//
//    @RequestMapping(value = "/getUserInfo")
//    public AjaxResult getUserInfo(HttpServletRequest request){
//        return sysUserService.findUserByWxTokenId(request.getParameter("wxTokenId"));
//    }
//
//    @RequestMapping(value = "/loginByPwd")
//    public AjaxResult loginByPwd(HttpServletRequest request){
//        return sysUserService.loginByPwd(request.getParameter("userName"), request.getParameter("pwd"));
//    }
//
//    @RequestMapping(value = "/loginOut")
//    public AjaxResult loginOut(HttpServletRequest request){
//        return sysUserService.loginOut(request.getParameter("id"));
//    }
//
//    @RequestMapping(value = "/uploadHeadImg")
//    public AjaxResult uploadHeadImg(HttpServletRequest request, @RequestParam("upload_img_head")MultipartFile[] files) {
//        return sysUserService.uploadHeadImg(request.getParameter("wxTokenId"), files);
//    }
//
//    @RequestMapping(value = "/sendCode")
//    public AjaxResult sendCode(HttpServletRequest request) {
//        return sysUserService.sendCode(request);
//    }
//
//    @RequestMapping(value = "/signUp")
//    public AjaxResult signUp(HttpServletRequest request) {
//        return sysUserService.signUp(request);
//    }
}
