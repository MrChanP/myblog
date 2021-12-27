package per.myblog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ajax请求返回工具类
 */
@Component
public class AjaxResult {

    //请求返回状态：0：成功   1：失败
    private int code;

    //返回消息
    private String msg;

    //请求成功时返回携带数据
    private Object data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    /**
     * 封装成功时返回数据
     * @param data
     * @return
     */
    public AjaxResult successData(Object data) {
        this.code = 0;
        this.data = data;
        this.msg = null;
        return this;
    }

    /**
     * 封装失败时返回消息
     * @param msg
     * @return
     */
    public AjaxResult failMsg(String msg) {
        this.code = 1;
        this.msg = msg;
        this.data = null;
        return this;
    }

    public String toJsonString() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();
        map.put("code", this.code);
        map.put("msg", this.msg);
        map.put("data", this.data);
        return objectMapper.writeValueAsString(map);
    }
}
