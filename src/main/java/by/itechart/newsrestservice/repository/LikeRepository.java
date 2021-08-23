package by.itechart.newsrestservice.repository;

import by.itechart.newsrestservice.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface LikeRepository extends JpaRepository<Like, Long> {
    Like findLikeByUserId(Long userId);

    @Query("SELECT COUNT(*) from Like WHERE is_downvoted=false AND news_id=:news_id")
    Integer getRating(@Param("news_id") long news_id);
}
