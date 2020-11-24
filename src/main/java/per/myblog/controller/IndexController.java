package per.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import per.myblog.service.SysUserService;
import per.myblog.service.WxTokenService;
import per.myblog.utils.AjaxResult;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    WxTokenService wxTokenService;

    @RequestMapping(value = "/getToken")
    public AjaxResult onLogin(HttpServletRequest request){
        return wxTokenService.getTokenByCode(request.getParameter("code"));
    }

    @RequestMapping(value = "/getUserInfo")
    public AjaxResult getUserInfo(HttpServletRequest request){
        return sysUserService.findUserByWxTokenId(request.getParameter("wxTokenId"));
    }

    @RequestMapping(value = "/loginByPwd")
    public AjaxResult loginByPwd(HttpServletRequest request){
        return sysUserService.loginByPwd(request.getParameter("userName"), request.getParameter("pwd"));
    }

    @RequestMapping(value = "/loginOut")
    public AjaxResult loginOut(HttpServletRequest request){
        return sysUserService.loginOut(request.getParameter("id"));
    }

    @RequestMapping(value = "/uploadHeadImg")
    public AjaxResult uploadHeadImg(HttpServletRequest request, @RequestParam("upload_img_head")MultipartFile[] files) {
        return sysUserService.uploadHeadImg(request.getParameter("wxTokenId"), files);
    }

    @RequestMapping(value = "/sendCode")
    public AjaxResult sendCode(HttpServletRequest request) {
        return sysUserService.sendCode(request);
    }

    @RequestMapping(value = "/signUp")
    public AjaxResult signUp(HttpServletRequest request) {
        return sysUserService.signUp(request);
    }

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, @RequestBody String userName){
//        emailUtils.send("chanpwork@163.com", "00000");
        return "hello world!-----" + request.getParameter("userName");
    }
}
