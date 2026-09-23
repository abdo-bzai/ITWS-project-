package rs.ac.singidunum.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.gym.entity.Member;
import rs.ac.singidunum.gym.entity.MembershipPlan;
import rs.ac.singidunum.gym.entity.Trainer;
import rs.ac.singidunum.gym.model.MemberModel;
import rs.ac.singidunum.gym.repo.MemberRepository;
import rs.ac.singidunum.gym.repo.MembershipPlanRepository;
import rs.ac.singidunum.gym.repo.TrainerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;
    private final TrainerRepository trainerRepository;
    private final MembershipPlanRepository planRepository;

    public List<Member> getMembers() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Member> getMemberById(Integer id) {
        return repository.findById(id);
    }

    public Member createMember(MemberModel model) {
        checkMember(model);
        if (repository.existsByEmail(model.getEmail().trim())) {
            throw new RuntimeException("A member with this email already exists");
        }

        Member member = new Member();
        fillMember(member, model);
        member.setCreatedAt(LocalDateTime.now());
        return repository.save(member);
    }

    public Member updateMember(Integer id, MemberModel model) {
        checkMember(model);
        Member existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        if (repository.existsByEmailAndIdNot(model.getEmail().trim(), id)) {
            throw new RuntimeException("A member with this email already exists");
        }

        fillMember(existing, model);
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void deleteMember(Integer id) {
        Member existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        repository.delete(existing);
    }

    // Copies values from the model to the entity, connects the plan and trainer,
    // and calculates the end date and total price
    private void fillMember(Member member, MemberModel model) {
        MembershipPlan plan = planRepository.findById(model.getPlanId())
                .orElseThrow(() -> new RuntimeException("Selected plan doesn't exist"));

        Trainer trainer = null;
        if (model.getTrainerId() != null) {
            trainer = trainerRepository.findById(model.getTrainerId())
                    .orElseThrow(() -> new RuntimeException("Selected trainer doesn't exist"));
        }

        member.setName(model.getName().trim());
        member.setEmail(model.getEmail().trim());
        member.setPlan(plan);
        member.setTrainer(trainer);
        member.setStartDate(model.getStartDate());
        member.setEndDate(model.getStartDate().plusMonths(plan.getDurationMonths()));
        member.setTotalPrice(calculatePrice(plan, trainer));
    }

    // Total = plan price + (trainer's monthly fee x number of months), no trainer = no fee
    private int calculatePrice(MembershipPlan plan, Trainer trainer) {
        int trainerCost = trainer == null ? 0 : trainer.getMonthlyFee() * plan.getDurationMonths();
        return plan.getPrice() + trainerCost;
    }

    private void checkMember(MemberModel model) {
        if (model.getName() == null || model.getName().isBlank()) {
            throw new RuntimeException("Member name is required");
        }
        if (model.getEmail() == null || !model.getEmail().contains("@")) {
            throw new RuntimeException("A valid email is required");
        }
        if (model.getStartDate() == null) {
            throw new RuntimeException("Start date is required");
        }
        if (model.getPlanId() == null) {
            throw new RuntimeException("Please choose a membership plan");
        }
    }
}
