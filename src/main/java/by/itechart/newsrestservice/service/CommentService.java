package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;

    @Autowired
    public CommentService(CommentRepository commentRepository, NewsService newsService, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public void addComment(News news, CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setComment(commentDto.getComment());
        comment.setNews(news);
        comment.setUser(userService.getCurrentUserByUsername());
        commentRepository.save(comment);
    }

    public void deleteById(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow();
    }
}
