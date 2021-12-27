package per.myblog.service;

import per.myblog.utils.AjaxResult;

public interface UsersService {

    AjaxResult login(String loginName, String password);

    AjaxResult register(String loginName, String password);
}
