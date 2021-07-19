package bot.botShiba.controller;

import bot.botShiba.model.User;
import bot.botShiba.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/joinForm")
    public String create() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setRole("ROLE_USER");
        userService.join(user);
        return "redirect:/index";
    }
}
