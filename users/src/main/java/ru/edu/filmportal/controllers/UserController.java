package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "Контроллер для работы с пользователем")
public class UserController {
    private final UserService service;

    @GetMapping("/all")
    @Operation(summary = "Получить всех пользователей")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти пользователя по Id")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/findByNickname/{nickname}")
    @Operation(summary = "Найти пользователя по никнэйму")
    public ResponseEntity<UserResponse> findByNickname(@PathVariable("nickname") String nickname) {
        return ResponseEntity.ok(service.findByNickname(nickname));
    }

    @PostMapping("/{id}/edit")
    @Operation(summary = "Изменить данные пользователя")
    public ResponseEntity<UserResponse> edit(@PathVariable("id") long id, @RequestBody @Validated UserEditRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}/delete")
    @Operation(summary = "Удалить пользователя")
    public ResponseEntity<MessageResponse> delete(@PathVariable("id") long id) {
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MessageResponse("User has been deleted"));
    }
}
