package rs.ac.singidunum.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.gym.entity.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    List<Member> findAllByOrderByNameAsc();

    boolean existsByTrainerId(Integer trainerId);

    boolean existsByPlanId(Integer planId);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Integer id);
}
