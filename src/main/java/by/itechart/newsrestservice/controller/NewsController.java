package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.exceptions.InvalidIdException;
import by.itechart.newsrestservice.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
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
            throw new Exception("Field id can't be empty!");
        }

        try {
            parsedId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new Exception("Invalid id!");
        }
        return newsService.findById(parsedId);
    }

    @GetMapping("/category/{category}")
    @ResponseBody
    public List<News> getNewsByCategory(@PathVariable("category") String category) throws Exception {
        List<News> newsByCategory;
        if (category == null || category.isBlank() || category.isEmpty()) {
            throw new Exception("Field category can't be empty!");
        }

        try {
            newsByCategory = newsService.findByCategory(NewsCategory.valueOf(category.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new Exception("Invalid category!");
        }
        return newsByCategory;
    }
}
