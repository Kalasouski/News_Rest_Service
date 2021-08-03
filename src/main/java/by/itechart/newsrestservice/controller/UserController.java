package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidIdException;
import by.itechart.newsrestservice.exceptions.InvalidUsernameException;
import by.itechart.newsrestservice.exceptions.UserException;
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

    @GetMapping("/id/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") String id) {
        long userId;
        try {
            userId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidIdException(e);
        }
        return userService.findById(userId);
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleUserException(UserException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping("/all")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/name/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable("username") String username) {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new NullPointerException("Field username can't be null!");
        }
        if (userService.findByUsername(username) == null) {
            throw new InvalidUsernameException("Can't find user with this username");
        }
        return userService.findByUsername(username);
    }

}
