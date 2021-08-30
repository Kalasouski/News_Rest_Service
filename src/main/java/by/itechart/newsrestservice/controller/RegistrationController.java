package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.RegistrationRequestDto;
import by.itechart.newsrestservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@RestController
public class RegistrationController {
    private final UserService userService;
    private static final String REGISTRATION_ERROR_MESSAGE = "User already exists! Try new username.";
    private static final String SUCCESSFULLY_REGISTRATION_MESSAGE = "User successfully registered!";

    @PostMapping("/signUp")
    public ResponseEntity<String> addNewUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        if (!userService.addNewUser(registrationRequestDto)) {
            return new ResponseEntity<>(REGISTRATION_ERROR_MESSAGE, HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(SUCCESSFULLY_REGISTRATION_MESSAGE);
    }
}
