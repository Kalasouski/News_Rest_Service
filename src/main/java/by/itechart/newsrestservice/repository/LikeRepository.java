package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findLikeByUserId(Long userId);
}
