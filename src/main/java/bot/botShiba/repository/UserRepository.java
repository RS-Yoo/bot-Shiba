package bot.botShiba.repository;

import bot.botShiba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    boolean existsUserByUserId(long userId);
    void deleteByUserId(long userId);
}
