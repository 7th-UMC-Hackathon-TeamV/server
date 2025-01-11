package banban.springboot.repository;

import banban.springboot.domain.entity.Member;
import banban.springboot.domain.entity.News;
import banban.springboot.domain.entity.TeamGroup;
import banban.springboot.domain.enums.NewsCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {
    List<News> findByIsBreakingNewsTrue(); // 속보 뉴스 조회
    List<News> findByNewsCategories(NewsCategories newsCategories); // 긍정/부정 뉴스 조회
    Optional<News> findByTeamGroupAndId(TeamGroup teamGroup, Long newsId);
    Optional<News> findByTeamGroupAndMemberAndId(TeamGroup teamGroup, Member member, Long newsId);
}
