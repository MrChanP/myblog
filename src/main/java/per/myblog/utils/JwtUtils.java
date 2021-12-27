package per.myblog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.Date;

public class JwtUtils {

    public static String createToken(String loginName, String password) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, Constants.SESSION_EXPIRE_SECONDS);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(loginName)   //签发对象
                .withIssuedAt(new Date())    //签发时间
                .withExpiresAt(expiresDate)  //有效时间
                .withClaim("password", password)    //载荷，随便写几个都可以
//                .withClaim("realName", realName)
                .sign(Algorithm.HMAC256(password));   //加密，用户密码作为秘钥
    }

    /**
     * 检验合法性，其中secret参数就应该传入的是用户的密码
     * @param token
     */
    public static void verifyToken(String token, String secret) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            //效验失败
            //这里抛出的异常是我自定义的一个异常，你也可以写成别的
        }
    }

    /**
     * 获取签发对象
     */
    public static String getAudience(String token) {
        String audience = null;
        try {
            audience = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            //这里是token解析失败
//            throw new TokenUnavailable();
        }
        return audience;
    }


    /**
     * 通过载荷名字获取载荷的值
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }
}
