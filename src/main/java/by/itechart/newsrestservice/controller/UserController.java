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

    @ExceptionHandler({InvalidInputFieldException.class,
            NumberFormatException.class,
            IllegalArgumentException.class})
    public ResponseEntity<String> numberFormatExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<UserDto> changeUserStatus(@PathVariable("id") Long id, @RequestBody String status) {
        User user = userService.findById(id);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setStatus(Status.valueOf(status.toUpperCase()));
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(UserDto.getUserDto(updatedUser));
    }

}