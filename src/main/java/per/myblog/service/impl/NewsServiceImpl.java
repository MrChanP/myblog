package per.myblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.myblog.entity.NewsDetails;
import per.myblog.entity.NewsTitle;
import per.myblog.repository.NewsDetailsRepository;
import per.myblog.repository.NewsTitleRepository;
import per.myblog.service.NewsService;
import per.myblog.utils.AjaxResult;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    private static final Logger log = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    NewsDetailsRepository newsDetailsRepository;
    @Autowired
    NewsTitleRepository newsTitleRepository;

    @Override
    public AjaxResult findAllNewsTitle() {
        AjaxResult ajaxResult = new AjaxResult();
        List<NewsTitle> list = newsTitleRepository.findAllNewsTitle();
        return ajaxResult.successData(list);
    }

    @Override
    public AjaxResult findNewsByNewsCode(String newsCode) {
        AjaxResult ajaxResult = new AjaxResult();
        log.info("newsCode：" + newsCode);
        List<Map<String, Object>> newsList = newsDetailsRepository.findNewsByNewsCode(newsCode);
        return ajaxResult.successData(newsList);
    }
}
