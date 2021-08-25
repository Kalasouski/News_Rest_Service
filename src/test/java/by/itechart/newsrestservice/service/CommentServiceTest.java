package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.dto.CommentDto;
import by.itechart.newsrestservice.entity.Comment;
import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.exceptions.NotFoundException;
import by.itechart.newsrestservice.provider.CommentServiceTestDataProvider;

import by.itechart.newsrestservice.provider.TestDataProvider;
import by.itechart.newsrestservice.repository.CommentRepository;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@WithUserDetails(value = "josh")
public class CommentServiceTest {

    @MockBean
    private CommentRepository commentRepository;



    @Autowired
    private CommentService commentService;

    @Test
    public void givenGetRequestToFindCommentById_whenIdIsValid_thenCommentShouldBeReturned() {
        given(commentRepository.findById(anyLong()))
                .willReturn(java.util.Optional.of(CommentServiceTestDataProvider.COMMENT));
        Comment comment = commentService.findById(CommentServiceTestDataProvider.DEFAULT_COMMENT_ID);
        assertEquals(CommentServiceTestDataProvider.COMMENT,comment);
    }

    @Test
    public void givenGetRequestToFindCommentById_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        given(commentRepository.findById(anyLong())).willThrow(NotFoundException.class);
        assertThrows(NotFoundException.class,
                () -> commentService.findById(CommentServiceTestDataProvider.DEFAULT_COMMENT_ID));
    }

    @Test
    public void givenPostRequestToSaveComment_whenRequestBodyIsValid_thenCommentShouldBeSaved() {
        given(commentRepository.save(any(Comment.class))).willReturn(CommentServiceTestDataProvider.COMMENT);
        Comment comment = commentService.addComment(CommentServiceTestDataProvider.NEWS,
                        CommentDto.getCommentDto(CommentServiceTestDataProvider.COMMENT));
        assertEquals(CommentServiceTestDataProvider.COMMENT,comment);
    }

    @Test
    public void givenPostRequestToSaveComment_whenRequestBodyIsinValid_thenExceptionShouldBeThrown() {
        given(commentRepository.save(any(Comment.class))).willThrow(NoSuchElementException.class);
        assertThrows(NoSuchElementException.class, () -> commentRepository.save(CommentServiceTestDataProvider.COMMENT));
    }
}
