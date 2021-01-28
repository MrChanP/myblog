package per.myblog.service;

import per.myblog.utils.AjaxResult;

public interface NewsService {

    AjaxResult findAllNewsTitle();

    AjaxResult findNewsByNewsCode(String newsCode);
}
