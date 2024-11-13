package ru.edu.filmportal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.User;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.response.UserResponse;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getNickname(),
                user.getEmail(),
                user.getHashPassword(),
                user.getRole(),
                user.getDataOfCreation());
    }
    public User toUser(UserCreateRequest userRequest) {
        return User.builder()
                .name(userRequest.name())
                .surname(userRequest.surname())
                .nickname(userRequest.nickname())
                .email(userRequest.email())
                .hashPassword(userRequest.hashPassword())
                .role(userRequest.role())
                .build();
    }
}
