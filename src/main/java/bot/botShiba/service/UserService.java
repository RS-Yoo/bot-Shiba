package bot.botShiba.service;

import bot.botShiba.model.User;
import bot.botShiba.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public int join(User user) {
        String rawPwd = user.getPassword();
        String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        user.setPassword(encPwd);
        userRepository.save(user);
        return user.getId();
    }

}
