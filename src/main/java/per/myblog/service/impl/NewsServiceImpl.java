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

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> rtnList = newsList.stream().map(map -> {
            Map<String, Object> tmpMap = new HashMap<>();
            tmpMap.put("newsId", map.get("newsId"));
            tmpMap.put("newsImg", map.get("news_img"));
            tmpMap.put("newsTitle", map.get("news_title"));
            tmpMap.put("authHead", map.get("head_url"));
            tmpMap.put("authName", map.get("user_name"));
            tmpMap.put("newsTime", sdf.format(map.get("news_time")));
            return tmpMap;
        }).collect(Collectors.toList());
        return ajaxResult.successData(rtnList);
    }
}
