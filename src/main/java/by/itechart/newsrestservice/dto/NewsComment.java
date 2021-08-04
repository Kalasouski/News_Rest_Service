package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewsComment {
    private final Long id;
    private final String comment;
    private final String userName;

    public static NewsComment provideNewsComment(Comment comment) {
        return new NewsComment(comment.getId(), comment.getComment(),comment.getUser().getFirstName());
    }
}
