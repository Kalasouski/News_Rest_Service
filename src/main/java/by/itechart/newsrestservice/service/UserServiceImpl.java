package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    User findById(Long id) {
        return userRepository.getById(id);
    }

    User save(User user) {
        return userRepository.save(user);
    }

    User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
