package ru.edu.filmportal.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.AuthException;
import ru.edu.filmportal.mappers.UserMapper;
import ru.edu.filmportal.models.database.Role;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.request.UserEditRequest;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(mapper::toUserResponse).toList();
    }

    public UserResponse findById(long userId) {
        return userRepository.findById(userId)
                .map(mapper::toUserResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found with the ID: " + userId));
    }

    public UserResponse findMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nickname;
        if (authentication != null) {
            nickname = (String) authentication.getPrincipal();
        } else {
            throw new AuthException("Unauthorized");
        }
        return findByNickname(nickname);
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
        user.setHashPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
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
        if(request.role() != null) {
            user.setRole(request.role());
        }

        return mapper.toUserResponse(userRepository.save(user));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
