package rs.ac.singidunum.gym.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.singidunum.gym.entity.MembershipPlan;

import java.util.List;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Integer> {

    List<MembershipPlan> findAllByOrderByDurationMonthsAsc();
}
