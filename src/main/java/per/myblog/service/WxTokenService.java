package per.myblog.service;

import per.myblog.entity.WxToken;
import per.myblog.utils.AjaxResult;

public interface WxTokenService {

    WxToken getWxTokenBySessionId(long sessionId);

    WxToken saveWxToken(String openId, String sessionKey);

    AjaxResult getTokenByCode(String code);
}
