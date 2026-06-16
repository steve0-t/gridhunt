package gamestudio.server.webservice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gamestudio.entity.Score;
import gamestudio.service.ScoreService;

@RestController
@RequestMapping("/api/score")
public class ScoreServiceRest {
  @GetMapping("/{game}")
  public List<Score> getTopScores(@PathVariable String game) {
    return scoreService.getTopScores(game);
  }

  @PostMapping
  public void addScore(@RequestBody Score score) {
    scoreService.addScore(score);
  }

  @Autowired private ScoreService scoreService;
}
