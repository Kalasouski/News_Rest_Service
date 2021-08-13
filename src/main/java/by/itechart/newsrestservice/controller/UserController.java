package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.UserDto;
import by.itechart.newsrestservice.entity.Status;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
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

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id) {
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
        return ResponseEntity.ok(UserDto.getUserDto(user));
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userService.findAllUsers()) {
            userDtoList.add(UserDto.getUserDto(user));
        }
        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping("/admin/users/{id}")
    public ResponseEntity<UserDto> changeUserStatus(@PathVariable("id") String id, @RequestBody String status) {
        long parsedId;

        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Typo in field ID!");
        }

        User user = userService.findById(parsedId);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User with this ID is not found!");
        }

        User updatedUser;
        try {
            user.setStatus(Status.valueOf(status.toUpperCase()));
            updatedUser = userService.save(user);
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Status is not found!");
        }
        return ResponseEntity.ok(UserDto.getUserDto(updatedUser));
    }

}