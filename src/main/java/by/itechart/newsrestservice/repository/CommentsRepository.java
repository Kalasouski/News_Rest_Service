package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comment, Long> {
}
