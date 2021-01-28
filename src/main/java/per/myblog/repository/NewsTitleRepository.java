package per.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import per.myblog.entity.NewsTitle;

import java.util.List;

@Repository
public interface NewsTitleRepository extends JpaRepository<NewsTitle, Long> {

    @Query(value = "select * from news_title", nativeQuery = true)
    List<NewsTitle> findAllNewsTitle();
}
