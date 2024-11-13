package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.filmportal.models.database.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
