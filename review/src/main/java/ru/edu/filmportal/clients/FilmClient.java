package ru.edu.filmportal.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.edu.filmportal.models.responses.FilmResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "film",
        url = "${application.config.urls.film-url}"
)
public interface FilmClient {
    @GetMapping("/{id}")
    FilmResponse findById(@RequestHeader("Authorization") String token, @PathVariable("id") long id);
}
