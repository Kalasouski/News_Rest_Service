package by.itechart.newsrestservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends InvalidInputFieldException {

    public NotFoundException(HttpStatus status) {
        super(HttpStatus.NOT_FOUND);
    }

    public NotFoundException(HttpStatus status, String s) {
        super(status, s);
    }
}
