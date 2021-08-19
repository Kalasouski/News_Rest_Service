package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LikeDto {
    private Long id;
    private String username;

    public static LikeDto getLikeDto(Vote vote){
        return new LikeDto(vote.getId(), vote.getUser().getUsername());
    }
}
