package by.itechart.newsrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidIdException extends ResponseStatusException {
    public InvalidIdException(Exception e) {
        super(HttpStatus.BAD_REQUEST,"Some text", e);
    }


}
