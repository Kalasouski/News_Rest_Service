package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.provider.NewsTestDataProvider;
import by.itechart.newsrestservice.dto.NewsDto;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.repository.NewsCategoryRepository;
import by.itechart.newsrestservice.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
@WithUserDetails(value = "admin")
class NewsServiceTest {

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private NewsService newsService;


    @Test // TODO: 25.08.2021 fix null
    public void whenGetRequestToAllNewsIsPerformed_thenListOfNewsDtoShouldBeReturned() {
        Page<News> pages = new PageImpl<>(NewsTestDataProvider.DUMMY_NEWS_LIST);

        given(newsRepository.findAll(ArgumentMatchers.isA(Pageable.class))).willReturn(pages);

        List<NewsDto> result = newsService.getNews(NewsTestDataProvider.DEFAULT_PAGE_NUMBER);

        assertEquals(1, result.size());
        assertEquals(NewsTestDataProvider.DEFAULT_NEWS_HEADING, result.get(0).getHeading());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsValid_thenNewsShouldBeReturned() {
        given(newsRepository.findById(anyLong()))
                .willReturn(java.util.Optional.of(NewsTestDataProvider.DUMMY_NEWS));

        given(newsCategoryRepository.findById(anyLong()))
                .willReturn(Optional.of(NewsTestDataProvider.DUMMY_NEWS_CATEGORY));

        News foundNews = newsService.findById(NewsTestDataProvider.EXISTING_ENTITY_ID);

        assertEquals(1, foundNews.getId());
        assertEquals(NewsTestDataProvider.DEFAULT_NEWS_HEADING, foundNews.getHeading());
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(newsRepository.findById(anyLong())).willThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> newsService.findById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsValid_thenNewsShouldBeSaved() {
        given(newsRepository.save(any(News.class))).willReturn(NewsTestDataProvider.DUMMY_NEWS);
        given(newsCategoryRepository.findById(anyLong()))
                .willReturn(Optional.of(NewsTestDataProvider.DUMMY_NEWS_CATEGORY));

        News result = newsService.save(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO);

        assertEquals(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO.getContent(), result.getContent());
        assertEquals(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO.getHeading(), result.getHeading());
        assertEquals(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO.getBrief(), result.getBrief());
    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsInvalid_thenExceptionShouldBeThrown() {
        NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO.setCategoryId(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID);

        given(newsRepository.save(NewsTestDataProvider.DUMMY_NEWS)).willThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class, () -> newsService.save(NewsTestDataProvider.DUMMY_NEWS_TO_SAVE_DTO));
    }

    @Test
    public void givenRequestToDeleteNews_whenIdIsValid_thenDeletionShouldBeDone() {
        given(newsRepository.findById(NewsTestDataProvider.EXISTING_ENTITY_ID))
                .willReturn(Optional.of(NewsTestDataProvider.DUMMY_NEWS));

        ArgumentCaptor<News> valueCapture = ArgumentCaptor.forClass(News.class);

        doNothing()
                .when(newsRepository)
                .delete(valueCapture.capture());

        newsRepository.delete(NewsTestDataProvider.DUMMY_NEWS);

        verify(newsRepository, times(1)).delete(NewsTestDataProvider.DUMMY_NEWS);
        assertEquals(NewsTestDataProvider.DUMMY_NEWS, valueCapture.getValue());
        assertEquals(NewsTestDataProvider.DUMMY_NEWS.getId(), valueCapture.getValue().getId());
        assertEquals(NewsTestDataProvider.DUMMY_NEWS.getContent(), valueCapture.getValue().getContent());
    }

    @Test
    public void givenRequestToDeleteNews_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(newsRepository.findById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID))
                .willThrow(NoSuchElementException.class);

        assertThrows(NoSuchElementException.class,
                () -> newsService.deleteById(NewsTestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

}