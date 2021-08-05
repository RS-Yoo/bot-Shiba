package bot.botShiba.repository;

import bot.botShiba.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    List<Tweet> findByUserId(long userId);
    void deleteAllByUserId(long userId);
    void deleteByUserIdAndContent(long userId, String content);
}
