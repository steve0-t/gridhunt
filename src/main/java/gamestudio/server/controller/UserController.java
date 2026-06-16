package gamestudio.server.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.entity.User;
import gamestudio.service.UserService;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserController {
  @RequestMapping("/")
  public String index(HttpSession session, Model model) {
    model.addAttribute("isLogged", isLogged());
    model.addAttribute("username", loggedUser);
    return "index";
  }

  @GetMapping("/login")
  public String login(HttpSession session, Model model) {
    String username = (String) session.getAttribute("username");
    System.out.println(username);
    session.setAttribute("username", username);
    return "login";
  }

  @PostMapping("/login")
  public String login(HttpSession session, String username, String password) {
    if (username != null && password != null) {
      User user = userService.getUser(username);
      if (user != null) {
        if (encoder.matches(password, user.getPassword())) {
          loggedUser = user.getUsername();
          session.setAttribute("username", user.getUsername());
          return "redirect:/gridhunt";
        }
        return "redirect:/login?wrong_password";
      }
      return "redirect:/login?not_found";
    }
    return "login";
  }

  @RequestMapping("/login?error")
  public String loginError(Model model) {
    model.addAttribute("error", true);
    return "login";
  }

  @GetMapping("/register")
  public String register(HttpSession session, Model model) {
    return "register";
  }

  @PostMapping("/register")
  public String register(String username, String password, String repeatedPassword) {
    if (username == null || password == null || repeatedPassword == null) return "redirect:/";

    if (password.compareTo(repeatedPassword) != 0) return "redirect:/register?mismatch_password";

    User tempUser = userService.getUser(username);
    if (tempUser != null) {
      return "redirect:/register?error";
    }

    User newUser = new User(username, encoder.encode(password));
    userService.addUser(newUser);
    loggedUser = newUser.getUsername();

    return "redirect:/login";
  }

  @RequestMapping("/logout")
  public String logout(HttpSession session, Model model) {
    if (session.getAttribute("username") != null) {
      gridCtl.resetGame(session, model);
      session.invalidate();
      loggedUser = null;
    }
    return "redirect:/";
  }

  @GetMapping("/change")
  public String changePassword() {
    return "change";
  }

  @PostMapping("/change")
  public String changePassword(
      HttpSession session, String username, String newPassword, String repeatPassword) {
    if (username == null || newPassword == null || repeatPassword == null)
      return "redirect:/change?error";

    System.out.println(username);
    System.out.println(newPassword);
    System.out.println(repeatPassword);

    if (newPassword.compareTo(repeatPassword) != 0) return "redirect:/change?no_match";

    User user = userService.getUser(username);
    System.out.println(user);
    if (user != null) {
      user.setPassword(encoder.encode(newPassword));
      userService.updatePassword(user);
      session.setAttribute("username", username);
      loggedUser = user.getUsername();
      return "redirect:/";
    }
    return "change";
  }

  public String getLoggedUser() {
    return loggedUser;
  }

  public boolean isLogged() {
    return loggedUser != null && !loggedUser.isEmpty();
  }

  private String loggedUser;

  private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(15);

  @Autowired private UserService userService;

  @Autowired GridhuntController gridCtl;
}
