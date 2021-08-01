package bot.botShiba.service;

import bot.botShiba.model.User;
import bot.botShiba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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


    public boolean twitterJoined(long userId, String screenName) {

        if(!userRepository.existsUserByUserId(userId)) {

            User user = new User();
            user.setUsername(screenName);
            user.setUserId(userId);

            user.setPassword("");
            user.setRole("ROLE_USER");
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }

    public void join(User user) {

        User initUser = userRepository.findByUsername(user.getUsername());

        String rawPwd = user.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        initUser.setPassword(encPwd);

        userRepository.save(initUser);
    }
}
