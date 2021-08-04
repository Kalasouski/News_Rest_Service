package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @ExceptionHandler(InvalidInputFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNewsException(InvalidInputFieldException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @GetMapping
    @ResponseBody
    public Map<Integer, String> getNewsHeadings() {
        Map<Integer, String> headers = new HashMap<>();
        List<News> news = newsService.findAll();
        for (int i = 0; i < news.size(); i++) {
            headers.put(i + 1, news.get(i).getHeading());
        }
        return headers;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public News getNewsById(@PathVariable("id") String id) throws Exception {
        long parsedId;
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Field ID can't be empty!");
        }

        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Type mismatch in field(s)!", e);
        }
        return newsService.findById(parsedId);
    }

    @GetMapping("/category/{category}")
    @ResponseBody
    public List<News> getNewsByCategory(@PathVariable("category") String category) throws Exception {
        List<News> newsByCategory;
        if (category == null || category.isBlank() || category.isEmpty()) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Field category can't be empty!");
        }

        try {
            newsByCategory = newsService.findByCategory(NewsCategory.valueOf(category.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Invalid category!");
        }
        return newsByCategory;
    }
}
