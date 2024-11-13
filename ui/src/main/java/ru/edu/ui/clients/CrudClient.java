package ru.edu.ui.clients;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudClient<T, K> {
    @GetMapping("/all")
    List<T> findAll();

    @PostMapping("/create")
    T create(@RequestBody K request);

    @PostMapping("/{id}/edit")
    T edit(@PathVariable("id") long id, @RequestBody K request);

    @DeleteMapping("/{id}/delete")
    void delete(@PathVariable("id") long id);
}
