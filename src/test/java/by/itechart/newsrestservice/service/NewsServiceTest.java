package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.TestDataProvider;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.repository.NewsRepository;
import by.itechart.newsrestservice.security.JwtTokenProvider;
import by.itechart.newsrestservice.security.UserDetailsImpl;
import by.itechart.newsrestservice.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@WithMockUser(username = "admin", password = "admin")
class NewsServiceTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CommentService commentService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserDetailsImpl userDetailsImpl;

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private NewsService newsService;

    @MockBean
    private LikeService likeService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void whenGetRequestToAllNewsIsPerformed_thenListOfNewsDtoShouldBeReturned() {

        given(newsRepository.findAll()).willReturn(TestDataProvider.testNewsList);

        given(newsService.getNews(1))
                .willReturn(NewsDto.getNewsDtoList(TestDataProvider.testNewsList));

        List<NewsDto> result = newsService.getNews(1);

        assertEquals(1, result.size());
        assertEquals(TestDataProvider.STRING_PLACEHOLDER, result.get(0).getHeading());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsValid_thenNewsShouldBeReturned() {
        given(newsRepository.findById(anyLong())).willReturn(java.util.Optional.of(TestDataProvider.testNews));
        given(newsService.findById(anyLong())).willReturn(TestDataProvider.testNews);

        News foundNews = newsService.findById(TestDataProvider.EXISTING_ENTITY_ID);

        assertEquals(1, foundNews.getId());
        assertEquals(TestDataProvider.STRING_PLACEHOLDER, foundNews.getHeading());
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(newsRepository.findById(anyLong())).willThrow(NotFoundException.class);
        given(newsService.findById(anyLong())).willThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> newsService.findById(TestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsValid_thenNewsShouldBeSaved() {
        given(newsRepository.save(TestDataProvider.testNews)).willReturn(TestDataProvider.testNews);
        given(newsService.save(TestDataProvider.testNewsToSaveDto)).willReturn(TestDataProvider.testNews);

        News result = newsService.save(TestDataProvider.testNewsToSaveDto);

        assertEquals(TestDataProvider.testNewsToSaveDto.getCategoryId(), result.getNewsCategory().getId());
        assertEquals(TestDataProvider.testNewsToSaveDto.getContent(), result.getContent());
        assertEquals(TestDataProvider.testNewsToSaveDto.getHeading(), result.getHeading());
        assertEquals(TestDataProvider.testNewsToSaveDto.getBrief(), result.getBrief());
    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsInvalid_thenExceptionShouldBeThrown() {
        TestDataProvider.testNewsToSaveDto.setCategoryId(TestDataProvider.NOT_EXISTING_ENTITY_ID);

        given(newsRepository.save(TestDataProvider.testNews)).willThrow(NoSuchElementException.class);
        given(newsService.save(TestDataProvider.testNewsToSaveDto)).willThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> newsService.save(TestDataProvider.testNewsToSaveDto));
    }

    @Test
    public void givenRequestToDeleteNews_whenIdIsValid_thenDeletionShouldBeDone() {
        given(newsRepository.findById(TestDataProvider.EXISTING_ENTITY_ID))
                .willReturn(Optional.of(TestDataProvider.testNews));

        ArgumentCaptor<News> valueCapture = ArgumentCaptor.forClass(News.class);

        doNothing()
                .when(newsRepository)
                .delete(valueCapture.capture());

        newsRepository.delete(TestDataProvider.testNews);

        verify(newsRepository, times(1)).delete(TestDataProvider.testNews);
        assertEquals(TestDataProvider.testNews, valueCapture.getValue());
        assertEquals(TestDataProvider.testNews.getId(), valueCapture.getValue().getId());
        assertEquals(TestDataProvider.testNews.getContent(), valueCapture.getValue().getContent());
    }

    @Test
    public void givenRequestToDeleteNews_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(newsRepository.findById(TestDataProvider.NOT_EXISTING_ENTITY_ID))
                .willThrow(NoSuchElementException.class);

        doThrow(NoSuchElementException.class)
                .when(newsService)
                .deleteById(TestDataProvider.NOT_EXISTING_ENTITY_ID);

        assertThrows(NoSuchElementException.class,
                () -> newsService.deleteById(TestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

}