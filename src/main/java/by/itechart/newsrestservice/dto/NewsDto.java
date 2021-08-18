package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDto {
    private Long id;
    private String heading;
    private String brief;
    private String content;
    private NewsCategory category;
    private List<CommentDto> comments;
    private int likes;

    public static NewsDto getNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setHeading(news.getHeading());
        newsDto.setBrief(news.getBrief());
        newsDto.setContent(news.getContent());
        newsDto.setCategory(news.getCategory());
        newsDto.setComments(news.getComments().stream().map(CommentDto::getCommentDto).collect(Collectors.toList()));
        newsDto.setLikes(news.getLikes().size());
        return newsDto;
    }

    public static List<NewsDto> getNewsDtoList(List<News> newsList) {
        return newsList.stream().map(NewsDto::getNewsDto).collect(Collectors.toList());
    }
}