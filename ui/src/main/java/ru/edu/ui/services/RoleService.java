package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.ui.clients.RoleClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleClient roleClient;

    public List<String> findAll() {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        return roleClient.getAll("Bearer " + token);
    }
}
