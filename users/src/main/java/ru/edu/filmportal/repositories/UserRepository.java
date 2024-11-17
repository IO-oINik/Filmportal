package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
