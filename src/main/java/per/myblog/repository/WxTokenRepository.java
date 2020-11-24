package per.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import per.myblog.entity.WxToken;

import java.sql.Timestamp;

@Repository
public interface WxTokenRepository extends JpaRepository<WxToken, Long> {

    @Query(value = "select * from wx_token where open_id = ?1", nativeQuery = true)
    WxToken findByOpenId(String openId);

    @Transactional
    @Modifying
    @Query(value = "insert into wx_token(open_id, session_key, create_time, update_time, use_able) " +
            "values (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    boolean addToken(String openId, String sessionKey, Timestamp createTime, Timestamp updateTime, String useAble);

    @Transactional
    @Modifying
    @Query(value = "update wx_token set session_key = ?2, update_time = ?3 where open_id = ?1", nativeQuery = true)
    boolean updateToken(String openId, String sessionKey, Timestamp updateTime);

}
