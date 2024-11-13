package ru.edu.filmportal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.request.UserEditRequest;
import ru.edu.filmportal.models.response.MessageResponse;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findByNickname/{nickname}")
    public ResponseEntity<UserResponse> findByNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(service.findByNickname(nickname));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody @Validated UserCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<UserResponse> edit(@PathVariable("id") long id, @RequestBody @Validated UserEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("User has been deleted"));
    }
}
