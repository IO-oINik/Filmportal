package ru.edu.filmportal.exceptions;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

public class UniqueFieldException extends RuntimeException{
    public UniqueFieldException(String msg){
        super(msg);
    }

}
