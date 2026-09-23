package rs.ac.singidunum.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.gym.entity.MembershipPlan;
import rs.ac.singidunum.gym.repo.MemberRepository;
import rs.ac.singidunum.gym.repo.MembershipPlanRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembershipPlanService {

    private final MembershipPlanRepository repository;
    private final MemberRepository memberRepository;

    public List<MembershipPlan> getPlans() {
        return repository.findAllByOrderByDurationMonthsAsc();
    }

    public Optional<MembershipPlan> getPlanById(Integer id) {
        return repository.findById(id);
    }

    public MembershipPlan createPlan(MembershipPlan plan) {
        checkPlan(plan);
        MembershipPlan newPlan = new MembershipPlan();
        newPlan.setName(plan.getName().trim());
        newPlan.setDurationMonths(plan.getDurationMonths());
        newPlan.setPrice(plan.getPrice());
        newPlan.setCreatedAt(LocalDateTime.now());
        return repository.save(newPlan);
    }

    public MembershipPlan updatePlan(Integer id, MembershipPlan plan) {
        checkPlan(plan);
        MembershipPlan existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
        existing.setName(plan.getName().trim());
        existing.setDurationMonths(plan.getDurationMonths());
        existing.setPrice(plan.getPrice());
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void deletePlan(Integer id) {
        MembershipPlan existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        if (memberRepository.existsByPlanId(id)) {
            throw new RuntimeException("Some members are on this plan. Move them to another plan first.");
        }

        repository.delete(existing);
    }

    private void checkPlan(MembershipPlan plan) {
        if (plan.getName() == null || plan.getName().isBlank()) {
            throw new RuntimeException("Plan name is required");
        }
        if (plan.getDurationMonths() == null || plan.getDurationMonths() < 1) {
            throw new RuntimeException("Duration must be at least 1 month");
        }
        if (plan.getPrice() == null || plan.getPrice() < 0) {
            throw new RuntimeException("Price can't be negative");
        }
    }
}
