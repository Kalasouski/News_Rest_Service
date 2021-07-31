package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    User findByUsername(String username);
}
