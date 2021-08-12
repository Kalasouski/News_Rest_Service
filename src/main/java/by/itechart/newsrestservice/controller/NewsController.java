
package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.AuthenticationRequestDto;

import by.itechart.newsrestservice.dto.NewsDto;
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
    public ResponseEntity<List<NewsDto>> getNewsList(){
        return new ResponseEntity<>(newsService.getNews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable("id") String id){
        long newsId;
        try {
            newsId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).build();
        }
        News news = newsService.findById(newsId);
        NewsDto newsDto = NewsDto.getNewsDto(news);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<NewsDto>> getNewsListByCategory(@PathVariable("category") String category){
        if (category == null || category.isBlank() || category.isEmpty()) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Field category can't be empty!");
        }
        try {
            List<NewsDto> newsDtoList = NewsDto.getNewsDtoList(newsService.
                    findByCategory(NewsCategory.valueOf(category.toUpperCase())));
            return new ResponseEntity<>(newsDtoList, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Invalid category!");
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<NewsDto> postCommentToNews(@PathVariable("id") String id, @RequestBody String comment) {
        long newsId;
        try {
            newsId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).build();
        }
        News news = newsService.findById(newsId);
        commentService.addComment(news, comment);
        NewsDto newsDto = NewsDto.getNewsDto(news);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }


    /*@DeleteMapping("/{news_id}/comments/{comment_id}")
    public News deleteCommentToNewsById(@PathVariable("news_id") String newsId,@PathVariable("comment_id") String commentId) {
        News news = this.getNewsById(newsId);
        Comment toDelete = newsService.getNewsCommentById(news,commentId);
        commentService.deleteCommentById(toDelete.getId());
        news.getComments().remove(Integer.parseInt(commentId));
        return news;
    }*/
}

