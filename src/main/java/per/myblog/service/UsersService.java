package per.myblog.service;

import org.springframework.stereotype.Service;
import per.myblog.utils.AjaxResult;

public interface UsersService {

    AjaxResult login(String loginName, String password);

    AjaxResult register(String loginName, String password);
}
