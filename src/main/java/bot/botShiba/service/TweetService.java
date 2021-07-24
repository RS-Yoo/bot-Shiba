package bot.botShiba.service;

import bot.botShiba.model.Tweet;
import bot.botShiba.repository.TweetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;

    public TweetService(TweetRepository tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public List<Tweet> findTweets(long userId) {
        return tweetRepository.findByUserId(userId);
    }
}
