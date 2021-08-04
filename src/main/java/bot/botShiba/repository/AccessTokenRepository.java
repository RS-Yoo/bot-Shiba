package bot.botShiba.repository;

import bot.botShiba.model.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
}
