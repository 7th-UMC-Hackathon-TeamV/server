package banban.springboot.repository;

import banban.springboot.domain.entity.TeamGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<TeamGroup, Long> {
    Optional<TeamGroup> findByGroupKey(String groupKey);
}
