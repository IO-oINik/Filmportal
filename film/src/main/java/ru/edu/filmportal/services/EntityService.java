package ru.edu.filmportal.services;

import java.util.List;

public interface EntityService<T, S, K>{
    T findById(long id);
    List<T> findAll();
    T create(S request);
    T update(long id, K request);
    void delete(long id);
}
