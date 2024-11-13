package ru.edu.filmportal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Person;
import ru.edu.filmportal.models.requests.PersonCreateRequest;
import ru.edu.filmportal.models.responses.PersonResponse;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    private final CountryMapper mapper;

    public PersonResponse toPersonResponse(Person person) {
        return new PersonResponse(person.getId(),
                person.getName(),
                person.getSurname(),
                person.getNameForeign(),
                person.getDateOfBirth(),
                mapper.toCountryResponse(person.getCountryOfBirth())
                );
    }

    public Person toPerson(PersonCreateRequest personCreateRequest) {
        return Person.builder()
                .name(personCreateRequest.name())
                .surname(personCreateRequest.surname())
                .nameForeign(personCreateRequest.nameForeign())
                .dateOfBirth(personCreateRequest.dateOfBirth())
                .build();
    }
}
