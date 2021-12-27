package per.myblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import per.myblog.annotation.NotVerifyToken;
import per.myblog.entity.Users;
import per.myblog.repository.UserRepository;
import per.myblog.utils.AjaxResult;
import per.myblog.utils.Constants;
import per.myblog.utils.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    /**
     * 调用controller之前拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("------------------URL:" + request.getRequestURI() + "--------------------");
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            // 获取类和方法上是否验证Token
            NotVerifyToken checkController = method.getClass().getAnnotation(NotVerifyToken.class);
            NotVerifyToken checkMehtod = method.getMethodAnnotation(NotVerifyToken.class);
//            // 类和方法均无需校验
            if (!((checkController == null || !checkController.value()) && (checkMehtod == null || !checkMehtod.value()))) {
                return true;
            }

            // 该方法需要校验
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()){
                String headerName = (String) headerNames.nextElement();
                System.out.println(headerName);
            }
            String token = request.getHeader("jwt_token");
            if (token != null) {
                // 获取用户信息
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                AjaxResult result = new AjaxResult();
                out = response.getWriter();
                ObjectMapper mapper = new ObjectMapper();
                Map map = mapper.readValue(token, Map.class);
                long cteateTime = (long) map.get("ctrate_time");
                if ((new Date().getTime() - cteateTime)/1000 < Constants.SESSION_EXPIRE_SECONDS) {
                    String jwt_token = map.get("jwt_token").toString();
                    String loginName = JwtUtils.getAudience(jwt_token);
                    Users users = userRepository.findByLoginName(loginName);
                    if (users != null) {
                        try {
                            JwtUtils.verifyToken(jwt_token, users.getPassword());
                            return true;
                        } catch (Exception e) {
                            out.append(result.failMsg("Token校验失败").toJsonString());
                            return false;
                        }
                    }
                }
                out.append(result.failMsg("请先登录").toJsonString());
            }
        }
        return false;
    }
}
