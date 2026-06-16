package gamestudio.server.controller;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import gamestudio.entity.Score;
import gamestudio.service.ScoreService;

/** Leaderboard */
@Controller
@RequestMapping("/leaderboard")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class LeaderboardController {
  @GetMapping
  public String leaderboard(HttpSession session, Model model) {
    String gameName = (String) session.getAttribute("gameName");
    System.out.println(gameName);
    List<Score> scores = scoreService.getTopScores(gameName);

    model.addAttribute("scores", scores);

    System.out.println(scores);

    return "leaderboard";
  }

  @Autowired ScoreService scoreService;
}
