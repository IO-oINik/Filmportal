package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Country;
import ru.edu.filmportal.models.requests.CountryRequest;
import ru.edu.filmportal.models.responses.CountryResponse;

@Component
public class CountryMapper {

    public CountryResponse toCountryResponse(Country country) {
        if (country == null) {
            return null;
        }
        return new CountryResponse(country.getId(), country.getTitle());
    }

    public Country toCountry(CountryRequest countryRequest) {
        if (countryRequest == null) {
            return null;
        }
        return new Country(countryRequest.title());
    }
}
