package per.myblog.config;

import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import per.myblog.controller.IndexController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebInterceptor implements HandlerInterceptor {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(IndexController.class);

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
        return true;
    }
}
