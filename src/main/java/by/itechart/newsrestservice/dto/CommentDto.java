package by.itechart.newsrestservice.dto;


import by.itechart.newsrestservice.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String comment;
    private String username;

    public static CommentDto getCommentDto(Comment comment){
        return new CommentDto(comment.getId(), comment.getComment(), comment.getUser().getUsername());
    }
}
