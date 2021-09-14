package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.dto.NewsToResponseDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.LikeService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = "http://localhost:3000/")
@RequiredArgsConstructor
@RestController
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final UserService userService;



    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNewsList(@RequestParam(defaultValue = "0") Integer pageNo,
                                                     @RequestParam(defaultValue = "3") Integer pageSize) {
        List<NewsDto> list = newsService.getNews(pageNo,pageSize);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/news/count")
    public ResponseEntity<Long> getNewsNumber() {
        Long number = newsService.getNewsNumber();
        return new ResponseEntity<>(number, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        News news = newsService.findById(id);
        return new ResponseEntity<>(NewsDto.getNewsDto(news), HttpStatus.OK);
    }

    @GetMapping("/news/category/{id}")
    public ResponseEntity<List<NewsDto>> getNewsListByCategory(@PathVariable Long id) {
        return new ResponseEntity<>(NewsDto.getNewsDtoList(newsService.findByCategoryId(id)), HttpStatus.OK);
    }

    @PostMapping("/news/{id}/comment")
    public ResponseEntity<CommentDto> postCommentToNews(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        News news = newsService.findById(id);
        Comment comment = commentService.addComment(news, commentDto);
        CommentDto responseCommentDto = CommentDto.getCommentDto(comment);
        return new ResponseEntity<>(responseCommentDto, HttpStatus.OK);
    }

    @PostMapping("/news")
    public ResponseEntity<NewsToResponseDto> saveNews(@RequestBody NewsToSaveDto newsToSaveDto) {
        if (newsToSaveDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        News savedNews = newsService.save(newsToSaveDto);
        return new ResponseEntity<>(new NewsToResponseDto(savedNews), HttpStatus.CREATED);
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<NewsToResponseDto> updateNews(@PathVariable Long id, @RequestBody NewsToSaveDto newsToSaveDto) {
        if (newsToSaveDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        News updatedNews = newsService.update(newsToSaveDto, id);
        return new ResponseEntity<>(new NewsToResponseDto(updatedNews), HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<NewsDto> deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/news/{id}/like")
    public ResponseEntity<Integer> likeNews(@PathVariable Long id) {
        News news = newsService.findById(id);
        likeService.voteForNews(news);
        int totalLikes = likeService.getTotalLikes(id);
        return new ResponseEntity<>(totalLikes, HttpStatus.OK);
    }

    @DeleteMapping("/news/{news_id}/comment/{id}")
    public ResponseEntity<NewsDto> deleteComment(@PathVariable Long news_id, @PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/news/category")
    public ResponseEntity<List<NewsCategory>> getAllNewsCategory() {
        List<NewsCategory> list = newsService.getNewsCategory();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementExceptionHandle() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }
}