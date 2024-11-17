package ru.edu.filmportal.exceptions;

public class InvalidToken extends RuntimeException{
    public InvalidToken(String message){
        super(message);
    }
}
