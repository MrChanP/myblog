package per.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.myblog.service.NewsService;
import per.myblog.utils.AjaxResult;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "/findAllNewsTitle")
    public AjaxResult findAllNewsTitle(){
        return newsService.findAllNewsTitle();
    }

    @RequestMapping(value = "/findNewsByNewsCode")
    public AjaxResult findNewsByNewsCode(HttpServletRequest request) {
        return newsService.findNewsByNewsCode(request.getParameter("newsCode"));
    }
}
