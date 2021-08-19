package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import by.itechart.newsrestservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;
    private final VoteService voteService;
    private final UserService userService;

    @GetMapping("/news")
    public ResponseEntity<List<NewsDto>> getNewsList(@RequestParam(defaultValue = "0") Integer pageNo) {
        List<NewsDto> list = newsService.getNews(pageNo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(NewsDto.getNewsDto(news), HttpStatus.OK);
    }

    @GetMapping("/news/category/{category}")
    public ResponseEntity<List<NewsDto>> getNewsListByCategory(@PathVariable Long id) {
        return new ResponseEntity<>(NewsDto.getNewsDtoList(newsService.findByCategory(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<NewsDto> postCommentToNews(@PathVariable("id") Long id, @RequestBody CommentDto commentDto) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.addComment(news, commentDto);
        NewsDto newsDto = NewsDto.getNewsDto(news);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NewsDto> saveNews(@RequestBody NewsDto newsDto) {
        if (newsDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newsService.save(newsDto);
        return new ResponseEntity<>(newsDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto newsDto, @PathVariable("id") Long id) {
        if (newsDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        newsService.update(newsDto, id);
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

    @PostMapping("/{id}/vote")
    public ResponseEntity<Integer> likeNews(@PathVariable Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        voteService.voteForNews(news);
        return new ResponseEntity<>(voteService.getNewsRating(id), HttpStatus.OK);
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

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> numberFormatExceptionHandle() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }
}