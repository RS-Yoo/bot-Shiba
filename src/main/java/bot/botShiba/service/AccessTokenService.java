package bot.botShiba.service;

import bot.botShiba.model.AccessToken;
import bot.botShiba.repository.AccessTokenRepository;
import bot.botShiba.service.encoder.Aes128;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class AccessTokenService {

    private final AccessTokenRepository accessTokenRepository;

    public AccessTokenService(AccessTokenRepository accessTokenRepository) {
        this.accessTokenRepository = accessTokenRepository;
    }

    public void storeToken(long userId, String token, String tokenSecret) throws Exception {

        String key = loadKey();
        Aes128 aes128 = new Aes128(key);

        String encToken = aes128.encrypt(token);
        String encTokenSecret = aes128.encrypt(tokenSecret);

        AccessToken accessToken = new AccessToken();
        accessToken.setUserId(userId);
        accessToken.setToken(encToken);
        accessToken.setTokenSecret(encTokenSecret);

        accessTokenRepository.save(accessToken);
    }

    private String loadKey() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("/Users/ry/Documents/Spring/bot-Shiba/src/main/java/bot/botShiba/service/key.properties");
            properties.load(inputStream);
    } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("key");
    }

}
