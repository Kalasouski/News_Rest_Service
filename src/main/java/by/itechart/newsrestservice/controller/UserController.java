package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidIdException;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/user/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") String id) {
        long userId;
        try{
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


  
}
