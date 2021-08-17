package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserService userService, NewsService newsService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.newsService = newsService;
    }

    public void addLikeToNews(News news) {
        Like like = new Like();
        like.setUser(userService.getCurrentUserByUsername());
        like.setNews(news);
        likeRepository.save(like);
    }

    public void removeLikeFromNews(Like like) {
        likeRepository.delete(like);
    }

    public List<Like> findLikeByUserId(Long id) {
        return likeRepository.findLikeByUserId(id);
    }
}
