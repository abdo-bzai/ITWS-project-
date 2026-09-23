package rs.ac.singidunum.gym.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


// It sends only the ids of the plan and trainer, not the whole objects.
@Getter
@Setter
@NoArgsConstructor
public class MemberModel {
    private String name;
    private String email;
    private LocalDate startDate;
    private Integer planId;
    private Integer trainerId; // null = trains alone
}
