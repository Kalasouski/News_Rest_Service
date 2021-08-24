package by.itechart.newsrestservice;

import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class TestDataProvider {

    public static final long EXISTING_ENTITY_ID = 1L;

    public static final long NOT_EXISTING_ENTITY_ID = 4L;

    public static final String STRING_PLACEHOLDER = "testStringForAnyField";

    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static User testUser;
    public static Comment testComment;
    public static List<Comment> testCommentsList;
    public static NewsCategory testNewsCategory;
    public static News testNews;
    public static Like testLike;
    public static NewsToSaveDto testNewsToSaveDto;
    public static List<Like> testLikesList;
    public static List<News> testNewsList;

    public TestDataProvider() {
        testUser = new User();
        testUser.setUsername(TestDataProvider.STRING_PLACEHOLDER);
        testUser.setId(TestDataProvider.EXISTING_ENTITY_ID);

        testComment = new Comment();
        testComment.setComment(TestDataProvider.STRING_PLACEHOLDER);
        testComment.setUser(testUser);

        testCommentsList = new ArrayList<>();
        testCommentsList.add(testComment);

        testNewsCategory = new NewsCategory();
        testNewsCategory.setName(TestDataProvider.STRING_PLACEHOLDER);
        testNewsCategory.setId(EXISTING_ENTITY_ID);

        testNews = new News();

        testLike = new Like();
        testLike.setUser(testUser);
        testLike.setId(EXISTING_ENTITY_ID);
        testLike.setNews(testNews);

        testLikesList = new ArrayList<>();
        testLikesList.add(testLike);

        testNews.setNewsCategory(testNewsCategory);
        testNews.setHeading(TestDataProvider.STRING_PLACEHOLDER);
        testNews.setId(EXISTING_ENTITY_ID);
        testNews.setComments(testCommentsList);
        testNews.setLikes(testLikesList);
        testNews.setContent(STRING_PLACEHOLDER);
        testNews.setBrief(STRING_PLACEHOLDER);

        testNewsList = new ArrayList<>();
        testNewsList.add(testNews);

        testNewsToSaveDto = new NewsToSaveDto();
        testNewsToSaveDto.setHeading(TestDataProvider.STRING_PLACEHOLDER);
        testNewsToSaveDto.setBrief(TestDataProvider.STRING_PLACEHOLDER);
        testNewsToSaveDto.setContent(TestDataProvider.STRING_PLACEHOLDER);
        testNewsToSaveDto.setCategoryId(EXISTING_ENTITY_ID);
    }


}
