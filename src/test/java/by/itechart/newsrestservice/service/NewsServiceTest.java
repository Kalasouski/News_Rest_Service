package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.TestDataProvider;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.repository.NewsRepository;
import by.itechart.newsrestservice.security.JwtTokenProvider;
import by.itechart.newsrestservice.security.UserDetailsImpl;
import by.itechart.newsrestservice.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@WithMockUser(username = "admin", password = "admin")
class NewsServiceTest {

    @MockBean
    private NewsService mockNewsService;

    @MockBean
    private UserService mockUserService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CommentService mockCommentService;

    @MockBean
    private LikeService mockLikeService;

    @MockBean
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;

    @MockBean
    private UserDetailsImpl mockUserDetailsImpl;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private LikeService likeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeTestMethod
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void whenGetRequestToAllNewsIsPerformed_thenListOfNewsDtoShouldBeReturned() {
        News testNews = new News();
        List<News> listOfNews = new ArrayList<>();
        testNews.setId(TestDataProvider.EXISTING_ENTITY_ID);
        testNews.setHeading(TestDataProvider.STRING_PLACEHOLDER);

        listOfNews.add(testNews);

        given(newsRepository.findAll()).willReturn(listOfNews); // TODO: 8/23/21 fix null pointer

        List<NewsDto> news = mockNewsService.getNews(TestDataProvider.DEFAULT_PAGE_NUMBER);

        assertEquals(1, news.size());
        assertEquals(1L, news.get(1).getId());
        assertEquals("testStringForAnyField", news.get(1).getHeading());

    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsValid_thenNewsShouldBeReturned() {
        News testNews = new News();
        testNews.setId(1L);

        given(newsRepository.findById(anyLong())).willReturn(java.util.Optional.of(testNews));

        News news = mockNewsService.findById(TestDataProvider.EXISTING_ENTITY_ID);
        assertEquals(1, news.getId());
    }

}