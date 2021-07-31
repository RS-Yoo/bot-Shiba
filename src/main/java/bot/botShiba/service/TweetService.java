package bot.botShiba.service;

import bot.botShiba.model.Tweet;
import bot.botShiba.model.User;
import bot.botShiba.repository.TweetRepository;
import bot.botShiba.repository.UserRepository;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final AccessTokenService accessTokenService;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository, AccessTokenService accessTokenService) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.accessTokenService = accessTokenService;
    }

    public void storeTweet(String content, long userId) {
        Tweet tweet = new Tweet();
        tweet.setContent(content);
        tweet.setUserId(userId);

        tweetRepository.save(tweet);
    }

    public List<Tweet> findTweets(long userId) {
        return tweetRepository.findByUserId(userId);
    }

    public void postTweets() throws Exception {

        List<User> users = userRepository.findAll();
        List<Tweet> tweets = new ArrayList<>();
        for(User user :users) {
            List<Tweet> userTweet = tweetRepository.findByUserId(user.getUserId());
            int index = getRandomNumber(0, userTweet.size());
            tweets.add(userTweet.get(index));
        }

        TwitterFactory factory = new TwitterFactory();
        Twitter twitter = factory.getInstance();
        AccessToken accessToken;

        for(Tweet tweet :tweets) {
            System.out.println(tweet.getUserId());
            accessToken = accessTokenService.retrieveToken(tweet.getUserId());
            twitter.setOAuthAccessToken(accessToken);
            twitter.updateStatus(tweet.getContent());
        }
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
