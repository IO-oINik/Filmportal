package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.edu.ui.models.requests.AuthRequest;
import ru.edu.ui.models.requests.UserCreateRequest;
import ru.edu.ui.models.responses.UserResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "auth",
        url = "${application.config.urls.auth-url}"
)
public interface AuthClient {
    @PostMapping("/register")
    UserResponse register(@RequestBody UserCreateRequest userCreateRequest);

    @PostMapping("/token/get")
    String getToken(@RequestBody AuthRequest authRequest);

    @GetMapping("/token/disable")
    void disableToken(@RequestHeader("Authorization") String token);
}
