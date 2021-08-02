package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") String id) throws Exception {
        if (id == null) {
            throw new Exception("Field ID can't be null!");
        }
        Long parsedId = Long.parseLong(id);
        return userService.findById(parsedId);
    }
  
}
