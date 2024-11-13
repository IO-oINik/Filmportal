package ru.edu.filmportal.models.requests;

public record ReviewEditRequest(
        String title,
        String text
) {
}
