package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;
    private final NewsService newsService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentsRepository commentsRepository, NewsService newsService, UserService userService) {
        this.commentsRepository = commentsRepository;
        this.newsService = newsService;
        this.userService = userService;
    }

    public boolean addComment(String newsId, String commentText) {
        News news = newsService.findById(newsId);
        Comment comment = new Comment();

        comment.setComment(commentText);
        comment.setNews(news);
        comment.setUser(userService.getCurrentUserUsername());

        commentsRepository.save(comment);
        return true;

    }

    public boolean removeComment(Long commentId) {
        commentsRepository.deleteCommentById(commentId);
        return true;
    }

}
