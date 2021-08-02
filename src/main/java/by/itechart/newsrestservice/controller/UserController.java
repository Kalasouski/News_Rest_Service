package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.service.UserService;
import org.json.JSONObject;
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

    @GetMapping( path="/{id}")
    public String getUser(@PathVariable("id") String id) {
        Long userId = Long.parseLong(id);
        JSONObject jsonObject = new JSONObject(userService.findById(userId));
        return jsonObject.toString();
    }
}
