package per.myblog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.myblog.entity.WxToken;
import per.myblog.repository.WxTokenRepository;
import per.myblog.service.WxTokenService;
import per.myblog.utils.AjaxResult;
import per.myblog.utils.DateUtils;
import per.myblog.utils.TencentApiUtils;

import java.sql.Timestamp;

/**
 * @author CHANP
 **/
@Service
public class WxTokenServiceImpl implements WxTokenService {

    @Autowired
    WxTokenRepository wxTokenRepository;
    @Autowired
    TencentApiUtils tencentApiUtils;

    @Override
    public WxToken getWxTokenBySessionId(long sessionId) {
//        return wxTokenRepository.getWxTokenBySessionId(sessionId);
        return wxTokenRepository.getOne(sessionId);
    }

    @Override
    public WxToken saveWxToken(String openId, String sessionKey) {
        WxToken wxToken = wxTokenRepository.findByOpenId(openId);
        Timestamp timestamp = DateUtils.getSQLDatatime();
        if (wxToken != null) {
            wxToken.setUpdateTime(timestamp);
            wxToken.setSessionKey(sessionKey);
            wxTokenRepository.saveAndFlush(wxToken);
            return wxToken;
        } else {
            WxToken newToken = new WxToken();
            newToken.setOpenId(openId);
            newToken.setSessionKey(sessionKey);
            newToken.setCreateTime(timestamp);
            newToken.setUpdateTime(timestamp);
//            newToken.setUseAble("0");
            wxTokenRepository.saveAndFlush(newToken);
            return newToken;
        }
    }

    @Override
    public AjaxResult getTokenByCode(String code) {
        AjaxResult result = new AjaxResult();
        WxToken wxToken = tencentApiUtils.getTokenByCode(code);
        return result.successData(wxToken.getId());
    }
}
