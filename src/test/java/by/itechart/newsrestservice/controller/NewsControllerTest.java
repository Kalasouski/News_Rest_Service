package by.itechart.newsrestservice.controller;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.dto.NewsToSaveDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.NewsCategory;
import by.itechart.newsrestservice.entity.Vote;
import by.itechart.newsrestservice.security.JwtTokenProvider;
import by.itechart.newsrestservice.security.UserDetailsImpl;
import by.itechart.newsrestservice.security.UserDetailsServiceImpl;
import by.itechart.newsrestservice.service.CommentService;
import by.itechart.newsrestservice.service.NewsService;
import by.itechart.newsrestservice.service.UserService;
import by.itechart.newsrestservice.service.VoteService;
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

import java.util.List;

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
    private VoteService voteService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserDetailsImpl userDetailsImpl;

    @MockBean
    private NewsToSaveDto newsToSaveDto;

    @MockBean
    private News mockedNews;

    @MockBean
    private NewsCategory mockedNewsCategory;

    @MockBean
    private List<Vote> mockedVotesList;

    @MockBean
    private List<News> mockNewsList;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final long EXISTING_ENTITY_ID = 1L;

    private static final long DUMMY_ENTITY_ID = 4L;

    private static final String STRING_PLACEHOLDER = "testStringForAnyField";

    @Before()
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void whenGetRequestToAllNewsIsSent_ThenStatusShouldBe200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/news"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestVoteIsSentAndNewsIsPresent_ThenStatusShouldBe200() throws Exception {
        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);

        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/vote", EXISTING_ENTITY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestVoteIsSentAndNewsIsNotPresent_ThenStatusShouldBe404() throws Exception {
        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);

        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/vote", DUMMY_ENTITY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenGetRequestToNewsIsSentAndNewsIsPresent_ThenStatusShouldBe200() throws Exception {
        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        Mockito.when(mockedNews.getNewsCategory()).thenReturn(mockedNewsCategory);
        Mockito.when(mockedNews.getNewsCategory().getName()).thenReturn(STRING_PLACEHOLDER);

        Mockito.doReturn(mockedVotesList).when(mockedNews).getVotes();
        Mockito.when(mockedNews.getVotes().size()).thenReturn(2);
        Mockito.when(Mockito.spy(mockedNews.getVotes())).thenReturn(mockedVotesList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/{id}", EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenGetRequestToNewsIsSentAndNewsIsNotPresent_ThenStatusShouldBe404() throws Exception {
        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/{id}", DUMMY_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenGetRequestToNewsByCategoryIsSentAndCategoryExists_ThenStatusShouldBe200() throws Exception {
        Mockito.when(mockedNewsCategory.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findByCategoryId(EXISTING_ENTITY_ID)).thenReturn(mockNewsList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/news/category/{id}", EXISTING_ENTITY_ID))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    // TODO: 8/20/21 add test if category list is empty

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndNewsExists_ThenStatusShouldBe200() throws Exception {
        CommentDto commentDto = new CommentDto(1L, STRING_PLACEHOLDER, STRING_PLACEHOLDER);

        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        Mockito.when(mockedNews.getNewsCategory()).thenReturn(mockedNewsCategory);
        Mockito.when(mockedNews.getNewsCategory().getName()).thenReturn(STRING_PLACEHOLDER);

        Mockito.doReturn(mockedVotesList).when(mockedNews).getVotes();
        Mockito.when(mockedNews.getVotes().size()).thenReturn(2);
        Mockito.when(Mockito.spy(mockedNews.getVotes())).thenReturn(mockedVotesList);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", EXISTING_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(commentDto)))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndNewsIsNotExist_ThenStatusShouldBe404() throws Exception {
        CommentDto commentDto = new CommentDto(1L, STRING_PLACEHOLDER, STRING_PLACEHOLDER);

        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", DUMMY_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(commentDto)))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    public void whenPostRequestToAddCommentToNewsIsSentAndCommentIsEmpty_ThenStatusShouldBe401()
            throws Exception {

        CommentDto commentDto = null;

        Mockito.when(mockedNews.getId()).thenReturn(EXISTING_ENTITY_ID);
        Mockito.when(newsService.findById(EXISTING_ENTITY_ID)).thenReturn(mockedNews);

        Mockito.when(mockedNews.getNewsCategory()).thenReturn(mockedNewsCategory);
        Mockito.when(mockedNews.getNewsCategory().getName()).thenReturn(STRING_PLACEHOLDER);

        Mockito.doReturn(mockedVotesList).when(mockedNews).getVotes();
        Mockito.when(mockedNews.getVotes().size()).thenReturn(2);
        Mockito.when(Mockito.spy(mockedNews.getVotes())).thenReturn(mockedVotesList);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news/{id}/comment", EXISTING_ENTITY_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(commentDto)))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void whenPostRequestToAddNewsIsSentAndNewsDtoIsNotEmpty_ThenStatusShouldBe201() throws Exception {
        NewsToSaveDto newsToSaveDto = new NewsToSaveDto();
        newsToSaveDto.setContent(STRING_PLACEHOLDER);
        newsToSaveDto.setHeading(STRING_PLACEHOLDER);
        newsToSaveDto.setBrief(STRING_PLACEHOLDER);
        newsToSaveDto.setCategoryId(mockedNewsCategory.getId());

        Mockito.when(newsService.save(newsToSaveDto)).thenReturn(mockedNews);
        Mockito.when(mockedNews.getNewsCategory()).thenReturn(mockedNewsCategory);
        Mockito.when(mockedNews.getNewsCategory().getName()).thenReturn(STRING_PLACEHOLDER);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(newsToSaveDto)))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    public void whenPostRequestToAddNewsIsSentAndNewsDtoIsEmpty_ThenStatusShouldBe400() throws Exception {
        NewsToSaveDto newsToSaveDto = null;

        Mockito.when(newsService.save(newsToSaveDto)).thenReturn(mockedNews);
        Mockito.when(mockedNews.getNewsCategory()).thenReturn(mockedNewsCategory);
        Mockito.when(mockedNews.getNewsCategory().getName()).thenReturn(STRING_PLACEHOLDER);

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