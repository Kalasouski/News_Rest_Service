package by.itechart.newsrestservice.provider;

import by.itechart.newsrestservice.entity.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class CommentServiceTestDataProvider {

    public static final String DEFAULT_USERNAME = "Username";
    public static final String DEFAULT_PASSWORD = "Password";

    public static final String DEFAULT_FIRST_NAME = "FirstName";
    public static final String DEFAULT_LAST_NAME = "LastName";
    public static final Role DEFAULT_ROLE = Role.ROLE_ADMIN;
    public static final Status DEFAULT_STATUS = Status.ACTIVE;
    public static final Long DEFAULT_USER_ID = 1L;
    public static final User USER = generateUser();

    private static User generateUser() {
        return User.builder()
                .id(DEFAULT_USER_ID)
                .username(DEFAULT_USERNAME)
                .password(DEFAULT_PASSWORD)
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .role(DEFAULT_ROLE)
                .status(DEFAULT_STATUS)
                .build();

    }


    public static final Long DEFAULT_NEWS_CATEGORY_ID = 1L;
    public static final String DEFAULT_NEWS_CATEGORY_NAME = "NewsCategory";
    public static final NewsCategory DEFAULT_NEWS_CATEGORY = generateNewsCategory();

    private static NewsCategory generateNewsCategory() {
        return NewsCategory.builder()
                .id(DEFAULT_NEWS_CATEGORY_ID)
                .name(DEFAULT_NEWS_CATEGORY_NAME)
                .build();
    }

    public static final Long DEFAULT_NEWS_ID = 1L;
    public static final String DEFAULT_NEWS_HEADING = "Heading";
    public static final String DEFAULT_NEWS_BRIEF = "Brief";
    public static final String DEFAULT_NEWS_CONTENT = "Content";
    public static final Timestamp DEFAULT_NEWS_TIMESTAMP = new Timestamp(1L);

    public static final News NEWS = generateNews();

    private static News generateNews() {
        return News.builder()
                .id(DEFAULT_NEWS_ID)
                .heading(DEFAULT_NEWS_HEADING)
                .brief(DEFAULT_NEWS_BRIEF)
                .content(DEFAULT_NEWS_CONTENT)
                .newsCategory(DEFAULT_NEWS_CATEGORY)
                .createdAt(DEFAULT_NEWS_TIMESTAMP)
                .build();
    }


    public static final Long DEFAULT_LIKE_ID = 1L;
    public static final Like LIKE = generateLike();

    private static Like generateLike() {
        return Like.builder()
                .id(DEFAULT_LIKE_ID)
                .news(NEWS)
                .user(USER)
                .build();
    }


    public static final Long DEFAULT_COMMENT_ID = 1L;
    private static final String DEFAULT_COMMENT_STRING = "Comment";
    public static final Comment COMMENT = generateComment();

    private static Comment generateComment() {
        return Comment.builder()
                .id(DEFAULT_COMMENT_ID)
                .comment(DEFAULT_COMMENT_STRING)
                .user(USER)
                .news(NEWS)
                .build();
    }

    static {
        USER.setComments(List.of(COMMENT));
        USER.setLikes(List.of(LIKE));
        NEWS.setComments(List.of(COMMENT));
        NEWS.setLikes(List.of(LIKE));
    }

}
