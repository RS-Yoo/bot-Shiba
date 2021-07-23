package bot.botShiba.service;

import bot.botShiba.model.User;
import bot.botShiba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
//@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public int join() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setUserId(1L);
        user.setRole("ROLE_USER");
        String rawPwd = user.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        user.setPassword(encPwd);
        userRepository.save(user);
        return user.getId();
    }


    public void twitterJoin(long userId, String screenName) {

        if(!userRepository.existsUserByUserId(userId)) {

            User user = new User();
            user.setUsername(screenName);
            user.setUserId(userId);

            String randPwd = UUID.randomUUID().toString();
            String encRandPwd = bCryptPasswordEncoder.encode(randPwd);

            user.setPassword(encRandPwd);
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }

        return;
    }
}
