package rs.ac.singidunum.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.gym.entity.Trainer;
import rs.ac.singidunum.gym.service.TrainerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/trainer")
public class TrainerController {

    private final TrainerService service;

    // READ all
    @GetMapping
    public List<Trainer> getAll() {
        return service.getTrainers();
    }

    // READ one
    @GetMapping(path = "/{id}")
    public ResponseEntity<Trainer> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getTrainerById(id));
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Trainer create(@RequestBody Trainer trainer) {
        return service.createTrainer(trainer);
    }

    // UPDATE
    @PutMapping(path = "/{id}")
    public Trainer update(@PathVariable Integer id, @RequestBody Trainer trainer) {
        return service.updateTrainer(id, trainer);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteTrainer(id);
    }
}
