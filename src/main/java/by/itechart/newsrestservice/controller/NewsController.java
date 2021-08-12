
package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

   //  ----------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<NewsDto> saveNews(@RequestBody NewsDto newsDto) {
        if (newsDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newsService.save(newsDto);
        return new ResponseEntity<>(newsDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto newsDto, @PathVariable("id") String id) {
        if (newsDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newsService.save(newsDto);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NewsDto> deleteNews(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/*/comment/{id}")
    public ResponseEntity<NewsDto> deleteComment(@PathVariable("id") Long id) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}