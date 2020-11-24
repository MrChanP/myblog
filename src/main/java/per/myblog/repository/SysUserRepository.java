package per.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import per.myblog.entity.SysUser;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    @Query(value = "select * from sys_user where (id = ?1 or user_name = ?1 or user_phone = ?1) and user_password = ?2", nativeQuery = true)
    SysUser loginByPwd(String userName, String pwd);

    @Query(value = "select * from sys_user where wx_token_id = ?1", nativeQuery = true)
    SysUser findUserByWxTokenId(String wxTokenId);

    @Transactional
    @Modifying
    @Query(value = "update sys_user set head_url = ?2 where wx_token_id = ?1", nativeQuery = true)
    int updateHeadUrl(String wxTokenId, String saveUrl);

    @Transactional
    @Modifying
    @Query(value = "update sys_user set login_state = 0 where id = ?1 ", nativeQuery = true)
    int updateLoginStatusById(String id);
}
