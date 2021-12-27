package per.myblog.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import per.myblog.entity.WxToken;
import per.myblog.service.WxTokenService;

import java.util.Map;

@Component
public class TencentApiUtils {

    private Logger log = LoggerFactory.getLogger(TencentApiUtils.class);
    @Value("${api-config.APP_ID}")
    private String APP_ID;
    @Value("${api-config.APP_SECRET}")
    private String APP_SECRET;

    @Autowired
    OkHttpUtils okHttpUtils;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    WxTokenService wxTokenService;

    /**
     * 获取令牌
     * @param code
     * @return
     */
    public WxToken getTokenByCode(String code){
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+APP_ID+"&secret="+APP_SECRET+"&js_code="+code+"&grant_type=authorization_code";
            String result = okHttpUtils.get(url);
            log.info("获取令牌结果：" + result);
            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(result, Map.class);
            if (map.get("openid") != null) {
                String openId = map.get("openid").toString();
                String sessionKey = map.get("session_key").toString();
                return wxTokenService.saveWxToken(openId, sessionKey);
            } else {
                log.info("code换取微信openId失败：" + map);
            }
        } catch (JsonMappingException e) {
            log.info("code换取微信openId异常："+e);
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
