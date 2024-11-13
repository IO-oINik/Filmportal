package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.edu.ui.models.requests.FilmRequest;
import ru.edu.ui.models.responses.FilmResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "film",
        url = "${application.config.urls.film-url}"
)
public interface FilmClient extends CrudClient<FilmResponse, FilmRequest>{
}
