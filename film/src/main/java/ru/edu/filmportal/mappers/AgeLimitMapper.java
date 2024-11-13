package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.AgeLimit;
import ru.edu.filmportal.models.requests.AgeLimitRequest;
import ru.edu.filmportal.models.responses.AgeLimitResponse;

@Component
public class AgeLimitMapper {
    public AgeLimitResponse toAgeLimitResponse(AgeLimit ageLimit) {
        return new AgeLimitResponse(ageLimit.getId(), ageLimit.getAge());
    }

    public AgeLimit toAgeLimit(AgeLimitRequest ageLimitRequest) {
        return new AgeLimit(ageLimitRequest.age());
    }
}
