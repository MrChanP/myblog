package per.myblog.utils;

import java.util.Random;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 生成一个新id
     * @return
     */
    public static long getNewId(){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<8;i++){
            //首字母不能为0
            sb.append(random.nextInt(9)+1);
        }
        return Long.parseLong(sb.toString());
    }

    public static String getCode(){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<6;i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 11位手机号加密
     * @param data
     * @return
     */
    public static String encodePhone(String data) {
        if (data != null && !"".equals(data)) {
            if (data.length() == 11) {
                return data.substring(0, 3) + "****" + data.substring(7);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
