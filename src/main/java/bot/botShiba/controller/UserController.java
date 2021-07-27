package bot.botShiba.controller;

import bot.botShiba.config.auth.PrincipalDetails;
import bot.botShiba.model.Tweet;
import bot.botShiba.model.User;
import bot.botShiba.service.AccessTokenService;
import bot.botShiba.service.TweetService;
import bot.botShiba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final TweetService tweetService;
    private final AccessTokenService accessTokenService;


    @Autowired
    public UserController(UserService userService, TweetService tweetService, AccessTokenService accessTokenService) {
        this.userService = userService;
        this.tweetService = tweetService;
        this.accessTokenService = accessTokenService;
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
    public String twitterOauth(HttpServletRequest request, Model model,
                               @RequestParam("oauth_token") String oauth_token,
                               @RequestParam("oauth_verifier") String oauth_verifier) throws Exception {

        Twitter twitter = new TwitterFactory().getInstance();
        AccessToken accessToken;

        RequestToken requestToken = (RequestToken) request.getServletContext().getAttribute("requestToken");
        request.getServletContext().removeAttribute("requestToken");


        accessToken = twitter.getOAuthAccessToken(requestToken, oauth_verifier);
        twitter.setOAuthAccessToken(accessToken);

        userService.twitterJoin(accessToken.getUserId(), accessToken.getScreenName());
        accessTokenService.storeToken(accessToken.getUserId(), accessToken.getToken(), accessToken.getTokenSecret());

        model.addAttribute("username", accessToken.getScreenName());

        return "/users/joinForm";

    }

    @PostMapping("/join")
    public String join(User user) {
        userService.join(user);
        return "/index";
    }


    @GetMapping("/users/tweets")
    public String list(Model model, @AuthenticationPrincipal PrincipalDetails principal) {
        List<Tweet> tweets = tweetService.findTweets(principal.getUserId());
        model.addAttribute("tweets", tweets);
        return "/users/tweetList";

    }

    @GetMapping("/users/home")
    public String userHome() {
        return "/users/home";
    }

}
