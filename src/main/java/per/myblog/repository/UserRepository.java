package per.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import per.myblog.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByLoginName(String loginName);
}
