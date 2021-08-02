package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable("id") String id) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("Field ID can't be empty!");
        }
        Long parsedId = null;
        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return userService.findById(parsedId);
    }

}
