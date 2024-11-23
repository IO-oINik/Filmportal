package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.vaadin.crudui.crud.Crud;
import ru.edu.ui.models.requests.AgeLimitRequest;
import ru.edu.ui.models.responses.AgeLimitResponse;

import java.util.List;

@FeignClient(
        name = "gateway-service",
        contextId = "ageLimit",
        url = "${application.config.urls.age-limit-url}"
)
public interface AgeLimitClient extends CrudClient<AgeLimitResponse, AgeLimitRequest> {
}
