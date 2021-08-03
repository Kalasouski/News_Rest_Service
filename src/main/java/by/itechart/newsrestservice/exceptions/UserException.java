package by.itechart.newsrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserException extends ResponseStatusException {

    public UserException() {
        super(HttpStatus.BAD_REQUEST);
    }

    public UserException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public UserException(Exception e) {
        super(HttpStatus.BAD_REQUEST, "", e);
    }
}
