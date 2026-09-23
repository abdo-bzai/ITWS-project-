package rs.ac.singidunum.gym.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rs.ac.singidunum.gym.entity.Trainer;
import rs.ac.singidunum.gym.repo.MemberRepository;
import rs.ac.singidunum.gym.repo.TrainerRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerRepository repository;
    private final MemberRepository memberRepository;

    public List<Trainer> getTrainers() {
        return repository.findAllByOrderByNameAsc();
    }

    public Optional<Trainer> getTrainerById(Integer id) {
        return repository.findById(id);
    }

    public Trainer createTrainer(Trainer trainer) {
        checkTrainer(trainer);
        Trainer newTrainer = new Trainer();
        newTrainer.setName(trainer.getName().trim());
        newTrainer.setSpecialty(trainer.getSpecialty().trim());
        newTrainer.setPhone(trainer.getPhone());
        newTrainer.setMonthlyFee(trainer.getMonthlyFee());
        newTrainer.setCreatedAt(LocalDateTime.now());
        return repository.save(newTrainer);
    }

    public Trainer updateTrainer(Integer id, Trainer trainer) {
        checkTrainer(trainer);
        Trainer existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        existing.setName(trainer.getName().trim());
        existing.setSpecialty(trainer.getSpecialty().trim());
        existing.setPhone(trainer.getPhone());
        existing.setMonthlyFee(trainer.getMonthlyFee());
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void deleteTrainer(Integer id) {
        Trainer existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        // Because of the relation, a trainer can't be deleted while members still point to them
        if (memberRepository.existsByTrainerId(id)) {
            throw new RuntimeException("This trainer still has members. Assign them to another trainer (or no trainer) first.");
        }

        repository.delete(existing);
    }

    private void checkTrainer(Trainer trainer) {
        if (trainer.getName() == null || trainer.getName().isBlank()) {
            throw new RuntimeException("Trainer name is required");
        }
        if (trainer.getSpecialty() == null || trainer.getSpecialty().isBlank()) {
            throw new RuntimeException("Trainer specialty is required");
        }
        if (trainer.getMonthlyFee() == null || trainer.getMonthlyFee() < 0) {
            throw new RuntimeException("Monthly fee is required and can't be negative");
        }
    }
}
