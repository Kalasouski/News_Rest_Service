package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidIdException;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/id/{id}")
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

    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidIdException(InvalidIdException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping("/user/all")
    @ResponseBody
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/user/name/{username}")
    @ResponseBody
    public User getUserByUsername(@PathVariable("username") String username) throws Exception {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new NullPointerException("Field username can't be null!");
        }
        if (userService.findByUsername(username) == null) {
            throw new Exception("User with this username is not found!");
        }
        return userService.findByUsername(username);
    }

}
