package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.edu.ui.models.requests.UserEditRequest;
import ru.edu.ui.models.responses.UserResponse;

import java.util.List;

@FeignClient(
        name = "gateway-service",
        contextId = "user",
        url = "${application.config.urls.user-url}"
)
public interface UserClient {
    @GetMapping("/all")
    List<UserResponse> findAll(@RequestHeader("Authorization") String token);

    @PostMapping("/{id}/edit")
    UserResponse edit(@PathVariable("id") long id, @RequestBody UserEditRequest request, @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}/delete")
    void delete(@PathVariable("id") long id, @RequestHeader("Authorization") String token);
}

