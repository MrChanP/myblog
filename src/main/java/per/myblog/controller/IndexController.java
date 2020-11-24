package per.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import per.myblog.service.SysUserService;
import per.myblog.service.WxTokenService;
import per.myblog.utils.AjaxResult;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/index")
public class IndexController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    WxTokenService wxTokenService;

    @RequestMapping(value = "/getToken")
    @ResponseBody
    public AjaxResult onLogin(HttpServletRequest request){
        return wxTokenService.getTokenByCode(request.getParameter("code"));
    }

    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public AjaxResult getUserInfo(HttpServletRequest request){
        return sysUserService.findUserByWxTokenId(request.getParameter("wxTokenId"));
    }

    @RequestMapping(value = "/loginByPwd")
    @ResponseBody
    public AjaxResult loginByPwd(HttpServletRequest request){
        return sysUserService.loginByPwd(request.getParameter("userName"), request.getParameter("pwd"));
    }

    @RequestMapping(value = "/loginOut")
    @ResponseBody
    public AjaxResult loginOut(HttpServletRequest request){
        return sysUserService.loginOut(request.getParameter("id"));
    }

    @RequestMapping(value = "/uploadHeadImg")
    @ResponseBody
    public AjaxResult uploadHeadImg(HttpServletRequest request, @RequestParam("upload_img_head")MultipartFile[] files) {
        return sysUserService.uploadHeadImg(request.getParameter("wxTokenId"), files);
    }

    @RequestMapping(value = "/sendCode")
    @ResponseBody
    public AjaxResult sendCode(HttpServletRequest request) {
        return sysUserService.sendCode(request);
    }

    @RequestMapping(value = "/signUp")
    @ResponseBody
    public AjaxResult signUp(HttpServletRequest request) {
        return sysUserService.signUp(request);
    }

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test(HttpServletRequest request, @RequestBody String userName){
//        emailUtils.send("chanpwork@163.com", "00000");
        return "hello world!-----" + request.getParameter("userName");
    }
}
