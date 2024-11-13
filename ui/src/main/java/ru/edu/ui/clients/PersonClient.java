package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edu.ui.models.requests.PersonRequest;
import ru.edu.ui.models.responses.CareersResponse;
import ru.edu.ui.models.responses.PersonResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "person",
        url = "${application.config.urls.person-url}"
)
public interface PersonClient extends CrudClient<PersonResponse, PersonRequest>{
    @GetMapping("/{id}/careers")
    public CareersResponse getCareers(@PathVariable("id") long id);
}
