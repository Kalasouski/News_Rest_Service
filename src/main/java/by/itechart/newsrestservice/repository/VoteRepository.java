package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Vote, Long> {
    List<Vote> findLikeByUserId(Long userId);
}
