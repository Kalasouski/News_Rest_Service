package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.NewsComment;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;

    @Autowired
    public NewsController(NewsService newsService, CommentService commentService) {
        this.newsService = newsService;
        this.commentService = commentService;
    }

    @ExceptionHandler(InvalidInputFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNewsException(InvalidInputFieldException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping
    public Map<Integer, String> getNewsHeadings() {
        return newsService.getNewsHeadings();
    }

    @GetMapping("/{id}")
    public News getNewsById(@PathVariable("id") String id) {
        News news = newsService.findById(id);
        if (news == null)
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No news with such id");
        return news;
    }


    @GetMapping("/category/{category}")
    public List<News> getNewsByCategory(@PathVariable("category") String category) {
        if (category == null || category.isBlank() || category.isEmpty()) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Field category can't be empty!");
        }
        try {
            return newsService.findByCategory(NewsCategory.valueOf(category.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Invalid category!");
        }
    }

    @GetMapping("/{id}/comments")
    public List<NewsComment> getNewsCommentsByUserId(@PathVariable("id") String id) {
        return this.getNewsById(id).getComments().stream().map(NewsComment::provideNewsComment).collect(Collectors.toList());
    }

    @PostMapping("/{id}/comments")
    public News postCommentToNews(@PathVariable("id") String id, @RequestBody String comment) {
        News news = newsService.findById(id);
        commentService.addComment(id, comment);
        return news;
    }

    @DeleteMapping("/{news_id}/comments/{comment_id}")
    public News deleteCommentToNewsById(@PathVariable("news_id") String newsId,@PathVariable("comment_id") String commentId) {
        News news = this.getNewsById(newsId);
        Comment toDelete = newsService.getNewsCommentById(news,commentId);
        commentService.deleteCommentById(toDelete.getId());
        return news;
    }
}
