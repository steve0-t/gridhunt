package gamestudio.server.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.service.RatingService;

/** RatingController */
@Controller
@RequestMapping("/rating")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class RatingController {
  @GetMapping
  public String leaderboard(HttpSession session, Model model) {
    return "rating";
  }

  @Autowired RatingService ratingService;
}
