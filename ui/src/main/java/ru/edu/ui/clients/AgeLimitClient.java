package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.edu.ui.models.requests.AgeLimitRequest;
import ru.edu.ui.models.responses.AgeLimitResponse;

import java.util.List;

@FeignClient(
        name = "gateway-service",
        contextId = "ageLimit",
        url = "${application.config.urls.age-limit-url}"
)
public interface AgeLimitClient {
    @GetMapping("/all")
    List<AgeLimitResponse> findAll();

    @PostMapping("/create")
    AgeLimitResponse create(@RequestBody AgeLimitRequest ageLimitRequest);

    @PostMapping("/{age-limit-id}/edit")
    AgeLimitResponse edit(@PathVariable("age-limit-id") long ageLimitId, @RequestBody AgeLimitRequest ageLimitRequest);

    @DeleteMapping("/{age-limit-id}/delete")
    void delete(@PathVariable("age-limit-id") long ageLimitId);
}
