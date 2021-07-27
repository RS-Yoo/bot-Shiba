package bot.botShiba.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class AccessToken {
    @Id
    private long userId;
    private String Token;
    private String TokenSecret;
}
