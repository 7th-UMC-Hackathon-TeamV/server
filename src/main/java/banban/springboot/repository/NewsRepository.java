package banban.springboot.repository;

import banban.springboot.domain.entity.News;
import banban.springboot.domain.entity.TeamGroup;
import banban.springboot.domain.enums.NewsCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    List<News> findByIsBreakingNewsTrue(); // 속보 뉴스 조회
    List<News> findByNewsCategories(NewsCategories newsCategories); // 긍정/부정 뉴스 조회

    List<News> findByTeamGroupAndCreatedAtBetween(TeamGroup teamGroup, LocalDateTime start, LocalDateTime end);
    void deleteByCreatedAtBefore(LocalDateTime dateTime);
}
