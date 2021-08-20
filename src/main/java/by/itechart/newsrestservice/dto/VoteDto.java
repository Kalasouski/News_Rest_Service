package by.itechart.newsrestservice.dto;

import by.itechart.newsrestservice.entity.Vote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoteDto {
    private Long id;
    private String username;
    private boolean isDownvoted;

    public static VoteDto getVoteDto(Vote vote){
        return new VoteDto(vote.getId(), vote.getUser().getUsername(), vote.isDownvoted());
    }
}
