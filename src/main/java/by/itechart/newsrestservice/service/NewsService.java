package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
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
}