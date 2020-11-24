package per.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author CHANP
 **/
@Component
public class SmsUtils {

    @Autowired
    RedisUtils redisUtils;

    public void send(String phone, String type){
        redisUtils.set(phone + "_" + type, "123456", 7200);
    }
}
