package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.User;

public interface UserService {

    User findById(Long id);

    User save(User user);

    User findByUsername(String username);
}
