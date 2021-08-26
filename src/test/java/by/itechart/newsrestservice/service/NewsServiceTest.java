package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.provider.TestDataProvider;
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
@WithUserDetails(value = "josh")
class NewsServiceTest {

    @MockBean
    private NewsRepository newsRepository;

    @MockBean
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private NewsService newsService;

    @Test
    public void whenGetRequestToAllNewsIsPerformed_thenListOfNewsDtoShouldBeReturned() {
        Page<News> pages = new PageImpl<>(TestDataProvider.testNewsList);

        given(newsRepository.findAll(ArgumentMatchers.isA(Pageable.class))).willReturn(pages);

        List<NewsDto> result = newsService.getNews(TestDataProvider.DEFAULT_PAGE_NUMBER);

        assertEquals(List.of(NewsDto.getNewsDto(TestDataProvider.testNews)),result);
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsValid_thenNewsShouldBeReturned() {
        given(newsRepository.findById(anyLong())).willReturn(java.util.Optional.of(TestDataProvider.testNews));

        News foundNews = newsService.findById(TestDataProvider.EXISTING_ENTITY_ID);

        assertEquals(TestDataProvider.testNews,foundNews);
    }

    @Test
    public void givenGetRequestToFindNewsById_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(newsRepository.findById(anyLong())).willThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,
                () -> newsService.findById(TestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsValid_thenNewsShouldBeSaved() {
        given(newsRepository.save(any(News.class))).willReturn(TestDataProvider.testNews);
        given(newsCategoryRepository.findById(anyLong())).willReturn(Optional.of(TestDataProvider.testNewsCategory));

        News result = newsService.save(TestDataProvider.testNewsToSaveDto);

        assertEquals(TestDataProvider.testNewsToSaveDto.getContent(), result.getContent());
        assertEquals(TestDataProvider.testNewsToSaveDto.getHeading(), result.getHeading());
        assertEquals(TestDataProvider.testNewsToSaveDto.getBrief(), result.getBrief());
    }

    @Test
    public void givenPostRequestToSaveNews_whenRequestBodyIsInvalid_thenExceptionShouldBeThrown() {
        TestDataProvider.testNewsToSaveDto.setCategoryId(TestDataProvider.NOT_EXISTING_ENTITY_ID);

        given(newsRepository.save(TestDataProvider.testNews)).willThrow(NoSuchElementException.class);

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

        assertThrows(NoSuchElementException.class,
                () -> newsService.deleteById(TestDataProvider.NOT_EXISTING_ENTITY_ID));

    }

}