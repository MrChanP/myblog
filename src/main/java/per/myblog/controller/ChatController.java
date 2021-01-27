package per.myblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import per.myblog.utils.AjaxResult;

import javax.servlet.http.HttpServletRequest;

/**
 * @author CHANP
 **/
@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    @RequestMapping(value = "/sendMsg")
    public AjaxResult sendMsg(HttpServletRequest request) {
        AjaxResult result = new AjaxResult();
        String sendTime = request.getParameter("sendTime");
        String sendMsg = request.getParameter("sendMsg");

        return result;
    }

}
