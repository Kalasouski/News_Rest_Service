package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.UserDto;
import by.itechart.newsrestservice.entity.Status;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @ExceptionHandler({NumberFormatException.class, IllegalArgumentException.class})
    public ResponseEntity<String> numberFormatExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementExceptionHandle() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
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
    public ResponseEntity<UserDto> changeUserStatus(@PathVariable Long id, @RequestBody String status) {
        User user = userService.findById(id);
        user.setStatus(Status.valueOf(status.toUpperCase()));
        User updatedUser = userService.save(user);
        return ResponseEntity.ok(UserDto.getUserDto(updatedUser));
    }
}