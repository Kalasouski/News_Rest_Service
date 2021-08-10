package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CommentsRepository extends JpaRepository<Comment, Long> {

}
