package per.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import per.myblog.entity.NewsDetails;

import java.util.List;
import java.util.Map;

@Repository
public interface NewsDetailsRepository extends JpaRepository<NewsDetails, Long> {

    @Query(value = "SELECT a.*, b.head_url, b.user_name FROM news_details a \n" +
            "JOIN sys_user b ON a.news_auth_id = b.id \n" +
            "WHERE a.news_code = ?1 ORDER BY a.news_time DESC ", nativeQuery = true)
    List<Map<String, Object>> findNewsByNewsCode(String newsCode);
}
