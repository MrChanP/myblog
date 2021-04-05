package per.myblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import per.myblog.entity.SysUser;
import per.myblog.repository.SysUserRepository;
import per.myblog.service.SysUserService;
import per.myblog.utils.*;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class SysUserServiceImpl implements SysUserService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    SysUserRepository sysUserRepository;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    EmailUtils emailUtils;
    @Autowired
    SmsUtils smsUtils;

    @Override
    public AjaxResult findUserByWxTokenId(String wxTokenId) {
        AjaxResult result = new AjaxResult();
        SysUser sysUser = sysUserRepository.findUserByWxTokenId(wxTokenId);
        if (sysUser != null && sysUser.getLoginState() == 1) {
            return result.successData(sysUser);
        } else {
            return result.failMsg("账号未登录");
        }
    }

    @Override
    public AjaxResult loginByPwd(String userName, String pwd) {
        AjaxResult result = new AjaxResult();
        SysUser sysUser = sysUserRepository.loginByPwd(userName, AesUtils.encrypt(pwd));
        if (sysUser != null) {
            if (sysUser.getLoginState() == 0) {
                sysUser.setLoginState(1);
                sysUserRepository.saveAndFlush(sysUser);
            }
            return result.successData(sysUser);
        } else {
            return result.failMsg("登录失败，请检查账号号和密码");
        }
    }

    @Override
    public AjaxResult uploadHeadImg(String wxTokenId, MultipartFile[] files) {
        AjaxResult result = new AjaxResult();
        log.info("进入头像上传...");
        if (files != null && files.length >= 1) {
            try {
                String fileName = files[0].getOriginalFilename();
                //判断是否有文件
                if (fileName != null) {
                    InputStream inStream = files[0].getInputStream();
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    //创建一个Buffer字符串
                    byte[] buffer = new byte[1024];
                    //每次读取的字符串长度，如果为-1，代表全部读取完毕'
                    int len = 0;
                    //使用一个输入流从buffer里把数据读取出来
                    while( (len = inStream.read(buffer)) != -1 ){
                        //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                        outStream.write(buffer, 0, len);
                    }
                    //关闭输入流
                    inStream.close();
                    //把outStream里的数据写入内存
                    byte[] data = outStream.toByteArray();
//                    String uploadPath = "/data/images/headImg/" + UUID.randomUUID().toString() + getEndFileName(fileName);
                    String uploadPath = "E:/project/myblog/images/headImg/" + UUID.randomUUID().toString() + getEndFileName(fileName);
                    log.info("头像path==" + uploadPath);

                    File imageFile = new File(uploadPath);
                    //创建输出流
                    FileOutputStream fileOutStream = new FileOutputStream(imageFile);
                    //写入数据
                    fileOutStream.write(data);
                    //关闭输出流
                    fileOutStream.close();


                    // 更新数据库用户头像地址
//                    String saveUrl = uploadPath.replace("/data/images/headImg/", "");
                    String saveUrl = uploadPath.replace("E:/project/myblog/images/headImg/", "");
                    sysUserRepository.updateHeadUrl(wxTokenId, saveUrl);

                    result.successData(saveUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result.failMsg("上传失败");
            }
        }
        return result;
    }

    @Override
    public AjaxResult loginOut(String id) {
        AjaxResult result = new AjaxResult();
        if (id != null && !"".equals(id)) {
            if (sysUserRepository.updateLoginStatusById(id) == 1) {

            } else {
                result.failMsg("退出失败！");
            }
        }
        return result;
    }

    @Override
    public AjaxResult sendCode(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        String wxTokenId = request.getParameter("wxTokenId");
        if (sysUserRepository.findUserByWxTokenId(wxTokenId) != null) {
            result.failMsg("该微信已注册过账号");
            return result;
        }
        String selectWay = request.getParameter("selectWay");
        String userName = request.getParameter("userName");
        String sendType = request.getParameter("sendType");
        if ("phone".equals(selectWay)) {
            smsUtils.send(userName, sendType);
        } else if ("mail".equals(selectWay)) {
            emailUtils.send(userName, sendType);
        }
        return result.successData("");
    }

    @Override
    public AjaxResult signUp(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        String userName = request.getParameter("userName");
        Object sessionCode = redisUtils.get(userName + "_01");
        if (sessionCode == null) {
            result.failMsg("验证码已失效，请重新发送");
            return result;
        }
        String code = request.getParameter("code");
        if (!sessionCode.toString().equals(code)) {
            result.failMsg("验证码错误");
            return result;
        }
        String selectWay = request.getParameter("selectWay");
        SysUser newUser = new SysUser();
        long id = StringUtils.getNewId();
        if ("phone".equals(selectWay)) {
            newUser.setUserPhone(userName);
        } else if ("mail".equals(selectWay)) {
            newUser.setEmail(userName);
        }
        newUser.setId(id);
        newUser.setWxTokenId(Long.parseLong(request.getParameter("wxTokenId")));
        newUser.setUserName(String.valueOf(id));
        newUser.setUserPassword(AesUtils.encrypt(request.getParameter("userPwd")));
        newUser.setLoginState(1);
        sysUserRepository.saveAndFlush(newUser);
        return result.successData(newUser);
    }

    private String getEndFileName(String fileName){
        if(fileName.endsWith(".jpg") || fileName.endsWith(".jepg")){
            return ".jpg";
        }else if(fileName.endsWith(".png") || fileName.endsWith(".PNG")){
            return ".png";
        } else{
            return "application/octet-stream";
        }
    }
}
