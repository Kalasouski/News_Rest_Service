package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
