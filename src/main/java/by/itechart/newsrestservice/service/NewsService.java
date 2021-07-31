package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;

import java.util.List;

public interface NewsService {
    News findById(Long id);

    List<News> findByCategory(NewsCategory category);

    List<News> findAll();

    News save(News news);
}
