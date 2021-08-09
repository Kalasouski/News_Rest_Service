package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User getCurrentUserByUsername() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "Can't find user with this username!");
        }
        return user;
    }
}