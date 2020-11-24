package per.myblog.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtils {

    /**
     * 获得一个Timestamp格式时间
     * @return
     */
    public static Timestamp getSQLDatatime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return java.sql.Timestamp.valueOf(df.format(new java.util.Date()));
    }
}
