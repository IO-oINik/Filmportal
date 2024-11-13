package ru.edu.filmportal.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.UserMapper;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.request.UserEditRequest;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(mapper::toUserResponse).toList();
    }

    public UserResponse findById(long userId) {
        return userRepository.findById(userId)
                .map(mapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found with the ID: " + userId));
    }

    public UserResponse findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .map(mapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found with the nickname: " + nickname));
    }

    public UserResponse create(UserCreateRequest request) {
        if(userRepository.existsByNickname(request.nickname())) {
            throw new EntityExistsException("User with the nickname: " + request.nickname() + " already exists");
        }
        if(userRepository.existsByEmail(request.email())) {
            throw new EntityExistsException("User with the email: " + request.email() + " already exists");
        }
        var user = mapper.toUser(request);
        return mapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse update(long id, UserEditRequest request) {
        var user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with the ID: " + id));

        if(request.name() != null && !request.name().isBlank()) {
            user.setName(request.name());
        }
        if(request.surname() != null && !request.surname().isBlank()) {
            user.setSurname(request.surname());
        }
        if(request.nickname() != null && !request.nickname().isBlank()) {
            user.setNickname(request.nickname());
        }
        if(request.email() != null && !request.email().isBlank()) {
            user.setEmail(request.email());
        }

        return mapper.toUserResponse(userRepository.save(user));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
