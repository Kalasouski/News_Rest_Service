package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.LikeService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final UserService userService;

    @Autowired
    public NewsController(NewsService newsService,
                          CommentService commentService,
                          LikeService likeService,
                          UserService userService) {
        this.newsService = newsService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getNewsList(
            @RequestParam(defaultValue = "0") Integer pageNo
            //@RequestParam(defaultValue = "2") Integer pageSize,
            //@RequestParam(defaultValue = "createdAt") String sortBy
    ) {
        List<NewsDto> list = newsService.getNews(pageNo);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(NewsDto.getNewsDto(news), HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<NewsDto>> getNewsListByCategory(@PathVariable("category") String category) {
        NewsCategory newsCategory;
        try {
            newsCategory = NewsCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<NewsDto> newsDtoList = NewsDto.getNewsDtoList(newsService.findByCategory(newsCategory));
        if (newsDtoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newsDtoList, HttpStatus.OK);
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

    @PostMapping("/{id}/like")
    public ResponseEntity<NewsDto> likeNews(@PathVariable("id") Long id) {
        News news = newsService.findById(id);
        if (news == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        likeService.addLikeToNews(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/dislike")
    public ResponseEntity<NewsDto> dislikeNews(@PathVariable("id") Long id) {
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