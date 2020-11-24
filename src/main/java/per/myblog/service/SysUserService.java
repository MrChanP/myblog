package per.myblog.service;

import org.springframework.web.multipart.MultipartFile;
import per.myblog.utils.AjaxResult;

import javax.servlet.http.HttpServletRequest;

public interface SysUserService {

    AjaxResult findUserByWxTokenId(String wxTokenId);

    AjaxResult loginByPwd(String userName, String pwd);

    AjaxResult uploadHeadImg(String wxTokenId, MultipartFile[] files);

    AjaxResult loginOut(String id);

    AjaxResult sendCode(HttpServletRequest request);

    AjaxResult signUp(HttpServletRequest request);
}
