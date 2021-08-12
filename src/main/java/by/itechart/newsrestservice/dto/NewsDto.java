package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {
    private Long id;
    private String heading;
    private String brief;
    private String content;
    private NewsCategory category;

    public News getNews() {
        News news = new News();
        news.setId(id);
        news.setHeading(heading);
        news.setBrief(brief);
        news.setContent(content);
        news.setCategory(category);
        news.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return news;
    }

    public static NewsDto getNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setHeading(news.getHeading());
        newsDto.setBrief(news.getBrief());
        newsDto.setContent(news.getContent());
        newsDto.setCategory(news.getCategory());
        return newsDto;
    }

}
