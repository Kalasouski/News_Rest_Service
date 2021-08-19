package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByNewsCategoryId(Long id);
}