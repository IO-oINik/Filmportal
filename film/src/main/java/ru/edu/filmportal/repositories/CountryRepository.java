package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    boolean existsByTitle(String title);
}
