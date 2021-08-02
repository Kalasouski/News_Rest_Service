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

    News findById(Long id) {
        return newsRepository.getById(id);
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    List<News> findByCategory(NewsCategory category) {
        return newsRepository.findByCategory(category);
    }

    News save(News news) {
        return newsRepository.save(news);
    }
}