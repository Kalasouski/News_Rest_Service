package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void voteForNews(News news) {
        User user = userService.getCurrentUserByUsername();

        for (Like like : user.getLikes()) {
            if (news.getLikes().contains(like)) {
                likeRepository.delete(like);
                return;
            }
        }

        Like like = new Like();
        like.setUser(user);
        like.setNews(news);
        likeRepository.save(like);
    }


    public int getTotalLikes(Long id) {
        return likeRepository.getTotalLikes(id);
    }

    public Like findVoteByUserId(Long id) {
        return likeRepository.findLikeByUserId(id);
    }

}

