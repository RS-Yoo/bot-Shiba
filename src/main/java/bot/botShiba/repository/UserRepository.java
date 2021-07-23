package bot.botShiba.repository;

import bot.botShiba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsUserByUserId(long userId);
}
