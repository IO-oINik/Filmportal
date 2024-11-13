package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.UserClient;
import ru.edu.ui.models.User;
import ru.edu.ui.models.requests.UserRequest;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserClient userClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll() {
        return userClient.findAll();
    }

    public User create(User user) {
        var userRequest = new UserRequest(user.getName(), user.getSurname(), user.getNickname(), user.getEmail(), bCryptPasswordEncoder.encode(user.getPassword()), user.getRole());
        try {
            return userClient.create(userRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        }
    }

    public User edit(User user) {
        var userRequest = new UserRequest(user.getName(), user.getSurname(), user.getNickname(), user.getEmail(), user.getPassword(), user.getRole());
        try {
            return userClient.edit(user.getId(), userRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        }
    }

    public void delete(User user) {
        userClient.delete(user.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userClient.findByNickname(username).orElseThrow(() -> new UsernameNotFoundException("User not found with nickname: " + username));
    }
}
