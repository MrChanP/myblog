package per.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import per.myblog.annotation.NotVerifyToken;
import per.myblog.service.SysUserService;
import per.myblog.service.UsersService;
import per.myblog.service.WxTokenService;
import per.myblog.utils.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/index")
public class IndexController {

    private static AjaxResult result = new AjaxResult();
    @Autowired
    SysUserService sysUserService;
    @Autowired
    UsersService usersService;
    @Autowired
    WxTokenService wxTokenService;
    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/test")
    public AjaxResult test(HttpServletRequest request, HttpServletResponse response){
//        emailUtils.send("chanpwork@163.com", "00000");
        return result.successData("Hello World!");
    }

    @RequestMapping(value = "/loginCheck")
    @NotVerifyToken
    public AjaxResult loginCheck(HttpServletRequest request){

        return result.successData("Hello World!---" + request.getParameter("userName"));
    }

    @RequestMapping(value = "/login")
    @NotVerifyToken
    public AjaxResult login(HttpServletRequest request){
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        result = usersService.login(loginName, password);
        if (result.getCode() == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("jwt_token", JwtUtils.createToken(loginName, password));
            map.put("ctrate_time", new Date().getTime());
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                result.successData(objectMapper.writeValueAsString(map));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
//            redisUtils.set("session_login_" + loginName, loginName, 10);
        }
        return result;
    }

    @RequestMapping(value = "/register")
    @NotVerifyToken
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
