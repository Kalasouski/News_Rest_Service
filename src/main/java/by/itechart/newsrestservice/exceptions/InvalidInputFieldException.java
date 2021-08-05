package by.itechart.newsrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInputFieldException extends ResponseStatusException {

    public InvalidInputFieldException(HttpStatus status) {
        super(status);
    }

    public InvalidInputFieldException(HttpStatus status, String s) {
        super(status, s);
    }

    public InvalidInputFieldException(HttpStatus status, String s, Exception e) {
        super(status, s, e);
    }
}
