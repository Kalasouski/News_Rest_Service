package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.exceptions.InvalidInputFieldException;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News findById(long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        newsRepository.deleteById(id);
    }



    public List<News> findByCategory(NewsCategory category) {
        return newsRepository.findByCategory(category);
    }

    public News save(NewsDto newsDto) {
        return newsRepository.save(newsDto.dtoToNews());
    }

    public News update(NewsDto newsDto, Long id) {
        News news = newsDto.dtoToNews();
        news.setId(id);
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
        List<News> news = newsRepository.findAll();
        for (int i = 0; i < news.size(); i++) {
            headers.put(i + 1, news.get(i).getHeading());
        }
        return headers;
    }

    public Comment getNewsCommentById(News news, String id) {
        int commentId;
        try {
            commentId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new InvalidInputFieldException(HttpStatus.BAD_REQUEST, "Type mismatch in field(s)!", e);
        }
        try {
            return news.getComments().get(commentId - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "No comment with such id");
        }
    }
    private static final Integer pageSize = 3;

    public static final String sortBy = "createdAt";

    public List<NewsDto> getNews(Integer pageNo) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        return newsRepository.findAll(paging).stream().map(NewsDto::getNewsDto).collect(Collectors.toList());
    }

}