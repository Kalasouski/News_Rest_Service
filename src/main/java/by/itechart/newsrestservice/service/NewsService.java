package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(String id) {
        Long newsId = checkId(id);
        return newsRepository.findById(newsId).orElse(null);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public List<News> findByCategory(NewsCategory category) {
        return newsRepository.findByCategory(category);
    }

    public News save(News news) {
        return newsRepository.save(news);
    }


    public Long checkId(String id) {
        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new InvalidInputFieldException(HttpStatus.NOT_FOUND, "Field ID can't be empty!");
        }
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Type mismatch in field(s)!", e);
        }
    }

    public Map<Integer, String> getNewsHeadings() {
        Map<Integer, String> headers = new HashMap<>();
        List<News> news = this.findAll();
        for (int i = 0; i < news.size(); i++) {
            headers.put(i + 1, news.get(i).getHeading());
        }
        return headers;
    }
}