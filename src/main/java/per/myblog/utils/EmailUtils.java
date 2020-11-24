package per.myblog.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author CHANP
 **/
@Component
public class EmailUtils {

    @Value("${spring.mail.username}")
    private String userName;
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    private JavaMailSender mailSender;

    public void send(String toEmail, String type) {
        String code = StringUtils.getCode();
        SimpleMailMessage message = new SimpleMailMessage();
        // 发件人
        message.setFrom(userName);
        // 收件人
        message.setTo(toEmail);
        // 邮件标题
        message.setSubject("验证");
        // 邮件内容
        message.setText("验证码：" + code + "，有效时间两小时");
        // 抄送人
//        message.setCc("xxx@qq.com");
        mailSender.send(message);
        redisUtils.set(toEmail + "_" + type, code, 7200);
    }
}
