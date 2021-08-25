package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.Like;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.provider.NewsTestDataProvider;
import by.itechart.newsrestservice.repository.NewsRepository;
import by.itechart.newsrestservice.security.JwtTokenProvider;
import by.itechart.newsrestservice.security.UserDetailsImpl;
import by.itechart.newsrestservice.security.UserDetailsServiceImpl;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.LikeService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.*;

import java.util.List;
import java.util.NoSuchElementException;

@WebMvcTest(controllers = NewsController.class)
@WithMockUser(username = "admin", password = "admin")
public class NewsControllerTest {

    @MockBean
    private NewsService newsService;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CommentService commentService;

    @MockBean
    private LikeService likeService;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserDetailsImpl userDetailsImpl;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private NewsToSaveDto newsToSaveDto;

    @MockBean
    private News news;

    @MockBean
    private NewsCategory newsCategory;

    @MockBean
    private List<Like> likes;

    @MockBean
    private List<News> newsList;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before()
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenGetRequestToAllNewsIsSent_thenStatusShouldBe200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestVoteIsSentAndNewsIsPresent_thenStatusShouldBe200() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);

        given(newsService.findById(NewsTestDataProvider.EXISTING_ENTITY_ID)).willReturn(news);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/like", NewsTestDataProvider.EXISTING_ENTITY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestVoteIsSentAndNewsIsNotPresent_thenStatusShouldBe404() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);

        given(newsService.findById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)).willThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/like", NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenGetRequestToNewsIsSentAndNewsIsPresent_thenStatusShouldBe200() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findById(NewsTestDataProvider.EXISTING_ENTITY_ID)).willReturn(news);

        given(news.getNewsCategory()).willReturn(newsCategory);
        given(news.getNewsCategory().getName()).willReturn(NewsTestDataProvider.STRING_PLACEHOLDER);

        Mockito.doReturn(likes).when(news).getLikes();
        given(news.getLikes().size()).willReturn(2);
        given(Mockito.spy(news.getLikes())).willReturn(likes);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/{id}", NewsTestDataProvider.EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenGetRequestToNewsIsSentAndNewsIsNotPresent_thenStatusShouldBe404() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)).willThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/{id}", NewsTestDataProvider.NOT_EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenGetRequestToNewsByCategoryIsSentAndCategoryExists_thenStatusShouldBe200() throws Exception {
        given(newsCategory.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findByCategoryId(NewsTestDataProvider.EXISTING_ENTITY_ID)).willReturn(newsList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/category/{id}", NewsTestDataProvider.EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenGetRequestToNewsByCategoryIsSentAndCategoryIsNotExist_thenStatusShouldBe404() throws Exception {
        given(newsCategory.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findByCategoryId(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)).willThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/category{id}", NewsTestDataProvider.NOT_EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndNewsExists_thenStatusShouldBe200() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findById(NewsTestDataProvider.EXISTING_ENTITY_ID)).willReturn(news);

        given(news.getNewsCategory()).willReturn(newsCategory);
        given(news.getNewsCategory().getName()).willReturn(NewsTestDataProvider.STRING_PLACEHOLDER);

        Mockito.doReturn(likes).when(news).getLikes();
        given(news.getLikes().size()).willReturn(2);
        given(Mockito.spy(news.getLikes())).willReturn(likes);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", NewsTestDataProvider.EXISTING_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(CommentDto.getCommentDto(NewsTestDataProvider.DUMMY_COMMENT))))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndNewsIsNotExist_thenStatusShouldBe404() throws Exception {
        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)).willThrow(NoSuchElementException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", NewsTestDataProvider.NOT_EXISTING_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(CommentDto.getCommentDto(NewsTestDataProvider.DUMMY_COMMENT))))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndCommentIsEmpty_thenStatusShouldBe401()
            throws Exception {

        CommentDto commentDto = null;

        given(news.getId()).willReturn(NewsTestDataProvider.EXISTING_ENTITY_ID);
        given(newsService.findById(NewsTestDataProvider.EXISTING_ENTITY_ID)).willReturn(news);

        given(news.getNewsCategory()).willReturn(newsCategory);
        given(news.getNewsCategory().getName()).willReturn(NewsTestDataProvider.STRING_PLACEHOLDER);

        Mockito.doReturn(likes).when(news).getLikes();
        given(news.getLikes().size()).willReturn(2);
        given(Mockito.spy(news.getLikes())).willReturn(likes);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", NewsTestDataProvider.EXISTING_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(commentDto)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void whenPostRequestToAddNewsIsSentAndNewsDtoIsNotEmpty_thenStatusShouldBe201() throws Exception {
        given(newsService.save(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO)).willReturn(NewsTestDataProvider.DUMMY_NEWS);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO)))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void whenPostRequestToAddNewsIsSentAndNewsDtoIsEmpty_thenStatusShouldBe400() throws Exception {
        NewsToSaveDto newsToSaveDto = null;

        given(newsService.save(newsToSaveDto)).willReturn(news);
        given(news.getNewsCategory()).willReturn(newsCategory);
        given(news.getNewsCategory().getName()).willReturn(NewsTestDataProvider.STRING_PLACEHOLDER);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(newsToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    public static String asJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
            mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            return writer.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}