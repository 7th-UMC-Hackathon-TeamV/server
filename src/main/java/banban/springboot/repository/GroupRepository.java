package banban.springboot.repository;

import banban.springboot.domain.entity.TeamGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<TeamGroup, Long> {
    boolean existsByGroupKey(String groupKey);
}
