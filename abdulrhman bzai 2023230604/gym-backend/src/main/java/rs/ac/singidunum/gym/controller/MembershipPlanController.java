package rs.ac.singidunum.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.gym.entity.MembershipPlan;
import rs.ac.singidunum.gym.service.MembershipPlanService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/plan")
public class MembershipPlanController {

    private final MembershipPlanService service;

    // READ all
    @GetMapping
    public List<MembershipPlan> getAll() {
        return service.getPlans();
    }

    // READ one
    @GetMapping(path = "/{id}")
    public ResponseEntity<MembershipPlan> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getPlanById(id));
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MembershipPlan create(@RequestBody MembershipPlan plan) {
        return service.createPlan(plan);
    }

    // UPDATE
    @PutMapping(path = "/{id}")
    public MembershipPlan update(@PathVariable Integer id, @RequestBody MembershipPlan plan) {
        return service.updatePlan(id, plan);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deletePlan(id);
    }
}
