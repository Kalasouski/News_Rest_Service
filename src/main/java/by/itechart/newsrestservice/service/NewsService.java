package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.repository.NewsCategoryRepository;
import by.itechart.newsrestservice.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsCategoryRepository newsCategoryRepository;


    @Value("${pagination.sort_by}")
    public String sortBy = "createdAt";

    @Value("${pagination.page_size}")
    private  Integer pageSize;

    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow();
    }

    public NewsCategory findNewsCategoryById(Long id){
        return newsCategoryRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        News news = findById(id);
        newsRepository.delete(news);
    }

    public Long getNewsNumber() {
        return newsRepository.count();
    }

    public List<News> findByCategoryId(Long id) {
        return newsRepository.findByNewsCategoryId(id);
    }

    public News save(NewsToSaveDto newsDto) {
        NewsCategory newsCategory = findNewsCategoryById(newsDto.getCategoryId());
        News news = newsDto.dtoToNews();
        news.setNewsCategory(newsCategory);
        return newsRepository.save(news);
    }

    public News update(NewsToSaveDto newsDto, Long id) {
        NewsCategory newsCategory = findNewsCategoryById(newsDto.getCategoryId());
        News news = newsDto.dtoToNews();
        news.setId(id);
        news.setNewsCategory(newsCategory);
        return newsRepository.save(news);
    }

    public List<NewsDto> getNews(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return newsRepository.findAll(paging).stream().map(NewsDto::getNewsDto).collect(Collectors.toList());
    }

    public List<NewsCategory> getNewsCategory() {
        return newsCategoryRepository.findAll();
    }
}