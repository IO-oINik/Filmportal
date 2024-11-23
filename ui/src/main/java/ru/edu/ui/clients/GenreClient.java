package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.edu.ui.models.requests.GenreRequest;
import ru.edu.ui.models.responses.GenreResponse;


@FeignClient(
        name = "gateway-service",
        contextId = "genre",
        url = "${application.config.urls.genre-url}"
)
public interface GenreClient extends CrudClient<GenreResponse, GenreRequest> {
}
