package bot.botShiba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BotShibaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotShibaApplication.class, args);
	}

}
