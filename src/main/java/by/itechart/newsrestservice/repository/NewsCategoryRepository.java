package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsCategoryRepository extends JpaRepository<NewsCategory, Long> {
}
