package banban.springboot.repository;

import banban.springboot.domain.entity.Member;
import org.apache.catalina.User;
import banban.springboot.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String userId);
}
