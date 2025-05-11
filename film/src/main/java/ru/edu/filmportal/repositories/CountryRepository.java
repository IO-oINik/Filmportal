package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.Country;

import java.util.Optional;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByTitle(String title);
    Optional<Country> findByTitle(String title);
}
