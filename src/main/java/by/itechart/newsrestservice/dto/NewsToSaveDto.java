package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsToSaveDto {
    private String heading;
    private String brief;
    private String content;
    private Long categoryId;

    public News dtoToNews() {
        News news = new News();
        news.setHeading(heading);
        news.setBrief(brief);
        news.setContent(content);
        news.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return news;
    }
}
