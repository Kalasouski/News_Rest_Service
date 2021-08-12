package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final NewsService newsService;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsService newsService, UserService userService) {
        this.commentRepository = commentRepository;
        this.newsService = newsService;
        this.userService = userService;
    }

    public boolean addComment(String newsId, String commentText) {
        News news = newsService.findById(newsId);
        Comment comment = new Comment();

        comment.setComment(commentText);
        comment.setNews(news);
        comment.setUser(userService.getCurrentUserByUsername());

        commentRepository.save(comment);
        return true;

    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }


    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }
}
