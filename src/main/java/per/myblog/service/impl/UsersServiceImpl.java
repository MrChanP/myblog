package per.myblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.myblog.entity.Users;
import per.myblog.repository.UserRepository;
import per.myblog.service.UsersService;
import per.myblog.utils.AjaxResult;
import per.myblog.utils.Sha1Util;

@Service
public class UsersServiceImpl implements UsersService {
    private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);
    private static final AjaxResult result = new AjaxResult();
    @Autowired
    UserRepository userRepository;

    @Override
    public AjaxResult login(String loginName, String password) {
        Users users = userRepository.findByLoginName(loginName);
        if (users != null) {
            String sha_password = Sha1Util.getSha1(password);
            if (!users.getPassword().equals(sha_password)) {
                return result.failMsg("密码错误");
            }
            return result.successData("");
        } else {
            return result.failMsg("用户不存在");
        }
    }

    @Override
    public AjaxResult register(String loginName, String password) {
        Users usersModel = new Users();
        usersModel.setLoginName(loginName);
        String sha1_password = Sha1Util.getSha1(password);
        usersModel.setPassword(sha1_password);
        userRepository.saveAndFlush(usersModel);
        return result.successData("");
    }

    public static void main(String[] args) {
        System.out.println(Sha1Util.getSha1("2"));
    }
}
