package by.itechart.newsrestservice;

import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class TestDataProvider {

    public static final long EXISTING_ENTITY_ID = 1L;

    public static final long NOT_EXISTING_ENTITY_ID = 4L;

    public static final String STRING_PLACEHOLDER = "testStringForAnyField";

    public static final int DEFAULT_PAGE_NUMBER = 3;

    public static final Timestamp DEFAULT_TIMESTAMP = new Timestamp(191919);

    public static final News NEWS = new News();

    public static final List<Like> LIKES_LIST = new ArrayList<>();

    public static final List<News> NEWS_LIST = new ArrayList<>();

    public static final List<NewsDto> NEWS_DTO_LIST = new ArrayList<>();

    public static final List<Comment> COMMENTS_LIST = new ArrayList<>();

    public static final NewsCategory NEWS_CATEGORY = new NewsCategory();

    public TestDataProvider() {
        LIKES_LIST.add(new Like());
        NEWS_LIST.add(NEWS);
        COMMENTS_LIST.add(new Comment());
        NEWS_DTO_LIST.add(new NewsDto());

        NEWS_CATEGORY.setName(STRING_PLACEHOLDER);
        NEWS_CATEGORY.setId(EXISTING_ENTITY_ID);

        NEWS.setNewsCategory(NEWS_CATEGORY);
        NEWS.setHeading(STRING_PLACEHOLDER);
        NEWS.setContent(STRING_PLACEHOLDER);
        NEWS.setBrief(STRING_PLACEHOLDER);
        NEWS.setId(EXISTING_ENTITY_ID);
        NEWS.setComments(COMMENTS_LIST);
        NEWS.setCreatedAt(DEFAULT_TIMESTAMP);
        NEWS.setLikes(LIKES_LIST);
    }
}
