package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.filmportal.models.database.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
