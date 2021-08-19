package by.itechart.newsrestservice.service;

import by.itechart.newsrestservice.entity.News;
import by.itechart.newsrestservice.entity.User;
import by.itechart.newsrestservice.entity.Vote;
import by.itechart.newsrestservice.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserService userService;
    private final NewsService newsService;

    @Autowired
    public VoteService(VoteRepository voteRepository, UserService userService, NewsService newsService) {
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.newsService = newsService;
    }

    public void voteForNews(News news) {
        User user = userService.getCurrentUserByUsername();

        for (Vote vote : user.getVotes()) {
            if (news.getVotes().contains(vote)) {
                voteRepository.delete(vote);
                return;
            }
        }

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setNews(news);
        vote.setDownvoted(false);
        voteRepository.save(vote);
    }


    public int getNewsRating(Long id) {
        return voteRepository.getRating(id);
    }

    public Vote findVoteByUserId(Long id) {
        return voteRepository.findVoteByUserId(id);
    }

}

