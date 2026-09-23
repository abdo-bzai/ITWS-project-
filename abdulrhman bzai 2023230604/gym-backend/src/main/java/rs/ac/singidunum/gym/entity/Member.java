package rs.ac.singidunum.gym.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // RELATION 1: every member has exactly one membership plan
    // (foreign key members.plan_id -> membership_plans.plan_id)
    @ManyToOne(optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private MembershipPlan plan;

    // RELATION 2: a member can have one trainer, or none (trains alone)
    // (foreign key members.trainer_id -> trainers.trainer_id, can be NULL)
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    // Calculated when saving: start date + plan duration
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    // Calculated when saving: plan price + trainer fee for every month of the plan
    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Not stored in the database - calculated every time from today's date.
    // Sent to the frontend as "status".
    public String getStatus() {
        LocalDate today = LocalDate.now();
        if (today.isAfter(endDate)) {
            return "Expired";
        }
        if (!today.plusDays(7).isBefore(endDate)) {
            return "Expiring soon";
        }
        return "Active";
    }
}
