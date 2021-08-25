package by.itechart.newsrestservice.provider;

import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsTestDataProvider {

    public static final long EXISTING_ENTITY_ID = 1L;

    public static final long NOT_EXISTING_ENTITY_ID = 4L;

    public static final String STRING_PLACEHOLDER = "testStringForAnyField";

    public static final String DEFAULT_USERNAME = "Username";

    public static final String DEFAULT_NEWS_CATEGORY_NAME = "NewsCategoryName";

    public static final String DEFAULT_NEWS_HEADING = "NewsHeading";

    public static final String DEFAULT_NEWS_BRIEF = "NewsBrief";

    public static final String DEFAULT_NEWS_CONTENT = "NewsContent";

    public static final int DEFAULT_PAGE_NUMBER = 1;

    public static final User DUMMY_USER = generateUser();

    public static final Comment DUMMY_COMMENT = generateComment();

    public static final List<Comment> DUMMY_COMMENT_LIST = generateCommentList();

    public static final NewsCategory DUMMY_NEWS_CATEGORY = generateNewsCategory();

    public static final News DUMMY_NEWS = generateNews();

    public static final Like DUMMY_LIKE = generateLike();

    public static final NewsToSaveDto DUMMY_NEWS_TO_SAVE_DTO = generateNewsToSaveDto();

    public static final List<Like> DUMMY_LIKE_LIST = generateLikeList();

    public static final List<News> DUMMY_NEWS_LIST = generateNewsList();

    public static User generateUser() {
        return User.builder()
                .username(DEFAULT_USERNAME)
                .id(EXISTING_ENTITY_ID)
                .build();
    }

    public static Comment generateComment() {
        return Comment.builder()
                .comment(STRING_PLACEHOLDER)
                .user(DUMMY_USER)
                .build();
    }

    public static List<Comment> generateCommentList() {
        List<Comment> commentList = new ArrayList<>();
        commentList.add(DUMMY_COMMENT);
        return commentList;
    }

    public static NewsCategory generateNewsCategory() {
        NewsCategory newsCategory = new NewsCategory();
        newsCategory.setName(DEFAULT_NEWS_CATEGORY_NAME);
        newsCategory.setId(EXISTING_ENTITY_ID);
        return newsCategory;
    }

    public static News generateNews() {
        News news = new News();
        news.setNewsCategory(DUMMY_NEWS_CATEGORY);
        news.setHeading(DEFAULT_NEWS_HEADING);
        news.setId(EXISTING_ENTITY_ID);
        news.setComments(DUMMY_COMMENT_LIST);
        news.setLikes(DUMMY_LIKE_LIST);
        news.setContent(DEFAULT_NEWS_CONTENT);
        news.setBrief(DEFAULT_NEWS_BRIEF);
        return news;

    }

    public static Like generateLike() {
        Like like = new Like();
        like.setUser(DUMMY_USER);
        like.setId(EXISTING_ENTITY_ID);
        like.setNews(DUMMY_NEWS);
        return like;
    }

    public static List<Like> generateLikeList() {
        List<Like> likes = new ArrayList<>();
        likes.add(DUMMY_LIKE);
        return likes;
    }

    public static List<News> generateNewsList() {
        List<News> newsList = new ArrayList<>();
        newsList.add(DUMMY_NEWS);
        return newsList;
    }

    public static NewsToSaveDto generateNewsToSaveDto() {
        return NewsToSaveDto.builder()
                .heading(DEFAULT_NEWS_HEADING)
                .brief(DEFAULT_NEWS_BRIEF)
                .content(DEFAULT_NEWS_CONTENT)
                .categoryId(EXISTING_ENTITY_ID)
                .build();
    }

}
