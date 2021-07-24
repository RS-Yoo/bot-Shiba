package bot.botShiba.repository;

import bot.botShiba.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findByUserId(long userId);
}
