package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController1 {

    private final NewsService newsService;

    @Autowired
    public UserController1(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public ResponseEntity<List<News>> getNewsList(){
        return new ResponseEntity<>(newsService.getNewsSummary(), HttpStatus.OK);
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable("id") String id){
        return new ResponseEntity<>(newsService.findById(id), HttpStatus.OK);
    }


}
