package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.dto.NewsToResponseDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.LikeService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;
    private final LikeService likeService;
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

    @GetMapping("/news/category/{id}")
    public ResponseEntity<List<NewsDto>> getNewsListByCategory(@PathVariable Long id) {
        return new ResponseEntity<>(NewsDto.getNewsDtoList(newsService.findByCategoryId(id)), HttpStatus.OK);
    }

    @PostMapping("/news/{id}/comment")
    public ResponseEntity<NewsDto> postCommentToNews(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.addComment(news, commentDto);
        NewsDto newsDto = NewsDto.getNewsDto(news);
        return new ResponseEntity<>(newsDto, HttpStatus.OK);
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
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newsService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/news/{id}/like")
    public ResponseEntity<NewsDto> likeNews(@PathVariable Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeService.addLikeToNews(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}/dislike")
    public ResponseEntity<NewsDto> dislikeNews(@PathVariable Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Like> likes = likeService.findLikeByUserId(userService.getCurrentUserByUsername().getId());
        if (likes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Like like = likes.get(0);
        if (like == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        likeService.removeLikeFromNews(like);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/news/{news_id}/comment/{id}")
    public ResponseEntity<NewsDto> deleteComment(@PathVariable Long news_id, @PathVariable Long id) {
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