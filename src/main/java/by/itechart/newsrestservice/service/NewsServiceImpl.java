package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(Long id) {
        return newsRepository.getById(id);
    }

    @Override
    public List<News> findByCategory(NewsCategory category) {
        return null;
    }

    //my impl
    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public News save(News news) {
        return null;
    }
}