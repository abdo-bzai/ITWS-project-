package rs.ac.singidunum.gym.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.ac.singidunum.gym.entity.Member;
import rs.ac.singidunum.gym.entity.MembershipPlan;
import rs.ac.singidunum.gym.entity.Trainer;
import rs.ac.singidunum.gym.repo.MemberRepository;
import rs.ac.singidunum.gym.repo.MembershipPlanRepository;
import rs.ac.singidunum.gym.repo.TrainerRepository;

import java.time.LocalDate;

// Inserts sample data the first time the app starts (only when the tables are empty)
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final MembershipPlanRepository planRepository;
    private final TrainerRepository trainerRepository;
    private final MemberRepository memberRepository;

    @Override
    public void run(String... args) {
        if (planRepository.count() > 0 || trainerRepository.count() > 0) {
            return;
        }

        // The longer the plan, the cheaper each month:
        // 5000, 4500, 4000, 3500 RSD per month
        MembershipPlan monthly = plan("Monthly", 1, 5000);
        MembershipPlan quarterly = plan("Quarterly", 3, 13500);
        MembershipPlan halfYear = plan("6 months", 6, 24000);
        MembershipPlan yearly = plan("Yearly", 12, 42000);

        Trainer marko = trainer("Marko Petrovic", "Strength", "+381 64 111 2233", 3000);
        Trainer ana = trainer("Ana Jovanovic", "Yoga", "+381 63 444 5566", 2500);
        Trainer stefan = trainer("Stefan Nikolic", "Cardio", "+381 65 777 8899", 2000);

        LocalDate today = LocalDate.now();
        member("Luka Ilic", "luka.ilic@mail.com", monthly, marko, today.minusDays(40));
        member("Milica Stojanovic", "milica.s@mail.com", yearly, ana, today.minusMonths(4));
        member("Nikola Djordjevic", "nikola.dj@mail.com", quarterly, marko, today.minusMonths(3).plusDays(4));
        member("Jelena Popovic", "jelena.p@mail.com", monthly, stefan, today.minusDays(3));
        member("Petar Markovic", "petar.m@mail.com", halfYear, null, today.minusMonths(1));
        member("Sara Kovac", "sara.k@mail.com", monthly, null, today.minusDays(10));
    }

    private MembershipPlan plan(String name, int months, int price) {
        MembershipPlan p = new MembershipPlan();
        p.setName(name);
        p.setDurationMonths(months);
        p.setPrice(price);
        return planRepository.save(p);
    }

    private Trainer trainer(String name, String specialty, String phone, int monthlyFee) {
        Trainer t = new Trainer();
        t.setName(name);
        t.setSpecialty(specialty);
        t.setPhone(phone);
        t.setMonthlyFee(monthlyFee);
        return trainerRepository.save(t);
    }

    private void member(String name, String email, MembershipPlan plan, Trainer trainer, LocalDate start) {
        Member m = new Member();
        m.setName(name);
        m.setEmail(email);
        m.setPlan(plan);
        m.setTrainer(trainer);
        m.setStartDate(start);
        m.setEndDate(start.plusMonths(plan.getDurationMonths()));
        int trainerCost = trainer == null ? 0 : trainer.getMonthlyFee() * plan.getDurationMonths();
        m.setTotalPrice(plan.getPrice() + trainerCost);
        memberRepository.save(m);
    }
}
