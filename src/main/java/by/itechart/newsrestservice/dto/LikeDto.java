package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.Like;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeDto {
    private Long id;
    private String username;

    public static LikeDto getLikeDto(Like like){
        return new LikeDto(like.getId(), like.getUser().getUsername());
    }
}
