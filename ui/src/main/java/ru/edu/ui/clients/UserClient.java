package ru.edu.ui.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.edu.ui.models.User;
import ru.edu.ui.models.requests.UserRequest;

import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "gateway-service",
        contextId = "user",
        url = "${application.config.urls.user-url}"
)
public interface UserClient extends CrudClient<User, UserRequest>{
    @GetMapping("/findByNickname/{nickname}")
    Optional<User> findByNickname(@PathVariable String nickname);
}
