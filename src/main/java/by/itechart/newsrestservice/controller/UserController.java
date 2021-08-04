package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(InvalidInputFieldException.class)
    public ResponseEntity<String> handleUserException(InvalidInputFieldException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @GetMapping("/id/{id}")
    public User getUserById(@PathVariable("id") String id) {
        long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Incorrect format of field(s)! ", e);
        }
        User user = userService.findById(userId);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Can't find user with this username");
        }
        return user;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/name/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Field username can't be null!");
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Can't find user with this username");
        }
        return user;
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getUserCommentsByUserId(@PathVariable("id") String id) {
        return this.getUserById(id).getComments();
    }

}
