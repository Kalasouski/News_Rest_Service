package by.itechart.newsrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInputFieldException extends ResponseStatusException {

    public InvalidInputFieldException(HttpStatus status) {
        super(HttpStatus.BAD_REQUEST);
    }

    public InvalidInputFieldException(HttpStatus status, String s) {
        super(HttpStatus.BAD_REQUEST, s);
    }

    public InvalidInputFieldException(String s, Exception e) {
        super(HttpStatus.BAD_REQUEST, s, e);
    }

    public InvalidInputFieldException(HttpStatus status, String s, Exception e) {
        super(status, s, e);
    }
}
