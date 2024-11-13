package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.edu.ui.models.requests.GenreRequest;
import ru.edu.ui.models.responses.GenreResponse;

import java.util.List;

@FeignClient(
        name = "gateway-service",
        contextId = "genre",
        url = "${application.config.urls.genre-url}"
)
public interface GenreClient {

    @GetMapping("/all")
    List<GenreResponse> findAll();

    @PostMapping("/create")
    GenreResponse create(@RequestBody GenreRequest genreRequest);

    @PostMapping("/{genre-id}/edit")
    GenreResponse edit(@PathVariable("genre-id") long genreId, @RequestBody GenreRequest genreRequest);

    @DeleteMapping("/{genre-id}/delete")
    void delete(@PathVariable("genre-id") long genreId);
}
