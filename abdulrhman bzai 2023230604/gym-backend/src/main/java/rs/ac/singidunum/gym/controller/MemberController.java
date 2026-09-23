package rs.ac.singidunum.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.gym.entity.Member;
import rs.ac.singidunum.gym.model.MemberModel;
import rs.ac.singidunum.gym.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/member")
public class MemberController {

    private final MemberService service;

    // READ all
    @GetMapping
    public List<Member> getAll() {
        return service.getMembers();
    }

    // READ one
    @GetMapping(path = "/{id}")
    public ResponseEntity<Member> getById(@PathVariable Integer id) {
        return ResponseEntity.of(service.getMemberById(id));
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Member create(@RequestBody MemberModel model) {
        return service.createMember(model);
    }

    // UPDATE
    @PutMapping(path = "/{id}")
    public Member update(@PathVariable Integer id, @RequestBody MemberModel model) {
        return service.updateMember(id, model);
    }

    // DELETE
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.deleteMember(id);
    }
}
