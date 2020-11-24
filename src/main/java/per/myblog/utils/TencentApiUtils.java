package per.myblog.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import per.myblog.entity.WxToken;
import per.myblog.service.WxTokenService;

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
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.get("openid") != null) {
                String openId = jsonObject.getString("openid");
                String sessionKey = jsonObject.getString("session_key");
                return wxTokenService.saveWxToken(openId, sessionKey);
            } else {
                log.info("code换取微信openId失败："+jsonObject);
            }
        } catch (JSONException e) {
            log.info("code换取微信openId异常："+e);
        }
        return null;
    }
}
