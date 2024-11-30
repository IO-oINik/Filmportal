package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(
        name = "gateway-service",
        contextId = "role",
        url = "${application.config.urls.role-url}"
)
public interface RoleClient {
    @GetMapping("/all")
    List<String> getAll(@RequestHeader("Authorization") String token);
}
