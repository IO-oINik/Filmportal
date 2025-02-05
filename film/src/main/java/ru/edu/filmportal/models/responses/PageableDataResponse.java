package ru.edu.filmportal.models.responses;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PageableDataResponse<T> {
    private List<T> data;
    private long total;
}
