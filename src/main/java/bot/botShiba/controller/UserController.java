package bot.botShiba.controller;

import bot.botShiba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/joinForm")
    public String create() {
        userService.join();
        return "redirect:/index";
    }

    @GetMapping("/twitterLogin")
    public void twitterLogin(HttpServletRequest request, HttpServletResponse response) throws TwitterException, IOException {
        Twitter twitter = new TwitterFactory().getInstance(); // 트위터로 접근

        RequestToken requestToken = twitter.getOAuthRequestToken(); //성공 시 requestToken에 해당 정보 겨짐
        request.getServletContext().setAttribute("requestToken", requestToken);


        String authURL = requestToken.getAuthenticationURL();

        response.sendRedirect(authURL);
    }

    @GetMapping("/twitterAuth")
    public String twitterOauth(HttpServletRequest request,
                               @RequestParam("oauth_token") String oauth_token,
                               @RequestParam("oauth_verifier") String oauth_verifier) throws TwitterException {

        Twitter twitter = new TwitterFactory().getInstance();
        AccessToken accessToken;

        RequestToken requestToken = (RequestToken) request.getServletContext().getAttribute("requestToken");
        request.getServletContext().removeAttribute("requestToken");


        accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
        twitter.setOAuthAccessToken(accessToken);

        System.out.println(accessToken.getUserId());    //트위터의 사용자 아이디
        System.out.println(accessToken.getScreenName());

        return "/user";
    }


}
