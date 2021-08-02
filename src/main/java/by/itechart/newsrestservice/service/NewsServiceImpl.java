package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    News findById(Long id) {
        return newsRepository.getById(id);
    }
}