package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Value("${pagination.page_size}")
    private Integer pageSize;
    @Value("${pagination.sort_by}")
    public String sortBy = "createdAt";

    public News findById(long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> findByCategory(NewsCategory category) {
        return newsRepository.findByCategory(category);
    }

    public News save(NewsToSaveDto newsDto) {
        return newsRepository.save(newsDto.dtoToNews());
    }

    public News update(NewsToSaveDto newsDto, Long id) {
        News news = newsDto.dtoToNews();
        news.setId(id);
        return newsRepository.save(news);
    }

    public List<NewsDto> getNews(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return newsRepository.findAll(paging).stream().map(NewsDto::getNewsDto).collect(Collectors.toList());
    }
}