package ru.edu.ui.clients;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudClient<T, K> {
    @GetMapping("/all")
    List<T> findAll();

    @PostMapping("/create")
    T create(@RequestBody K request, @RequestHeader("Authorization") String token);

    @PostMapping("/{id}/edit")
    T edit(@PathVariable("id") long id, @RequestBody K request, @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}/delete")
    void delete(@PathVariable("id") long id, @RequestHeader("Authorization") String token);
}
