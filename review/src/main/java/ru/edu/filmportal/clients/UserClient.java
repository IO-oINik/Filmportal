package ru.edu.filmportal.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.responses.UserResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "user",
        url = "${application.config.urls.user-url}"
)
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse findById(@RequestHeader("Authorization") String token, @PathVariable("id") long id);

    @GetMapping("/me")
    UserResponse findMe(@RequestHeader("Authorization") String token);
}

