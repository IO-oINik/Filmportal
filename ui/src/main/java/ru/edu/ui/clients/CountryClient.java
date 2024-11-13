package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import ru.edu.ui.models.requests.CountryRequest;
import ru.edu.ui.models.responses.CountryResponse;

@FeignClient(
        name = "gateway-service",
        contextId = "country",
        url = "${application.config.urls.country-url}"
)
public interface CountryClient extends CrudClient<CountryResponse, CountryRequest>{
}
