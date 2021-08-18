package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewsToResponseDto {
    private Long id;
    private String heading;
    private String brief;
    private String content;
    private NewsCategory category;
    private Timestamp createdAt;

    public NewsToResponseDto(News news) {
        this.id = news.getId();
        this.heading = news.getHeading();
        this.brief = news.getBrief();
        this.content = news.getContent();
        this.category = news.getCategory();
        this.createdAt = news.getCreatedAt();
    }
}
