package gamestudio.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.*;
import java.util.Date;
import org.junit.jupiter.api.*;
import gamestudio.entity.Score;

public class ScoreServiceTest {
  @BeforeAll
  public static void prepare() {
    scoreService.reset();
  }

  @AfterEach
  public void cleanup() throws SQLException {
    scoreService.reset();
  }

  @Test
  public void addScoreTest() {
    scoreService.reset();
    var date = new Date();

    scoreService.addScore(new Score("mines", "Jaro", 100, date));

    var scores = scoreService.getTopScores("mines");
    assertEquals(1, scores.size());
    assertEquals("mines", scores.get(0).getGame());
    assertEquals("Jaro", scores.get(0).getPlayer());
    assertEquals(100, scores.get(0).getPoints());
    assertEquals(date, scores.get(0).getPlayedOn());
  }

  @Test
  public void getTopScores() {
    scoreService.reset();
    var date = new Date();
    scoreService.addScore(new Score("mines", "Jaro", 120, date));
    scoreService.addScore(new Score("mines", "Katka", 150, date));
    scoreService.addScore(new Score("tiles", "Jozef", 180, date));
    scoreService.addScore(new Score("mines", "Michal", 100, date));

    var scores = scoreService.getTopScores("mines");

    assertEquals(3, scores.size());

    assertEquals("mines", scores.get(0).getGame());
    assertEquals("Katka", scores.get(0).getPlayer());
    assertEquals(150, scores.get(0).getPoints());

    assertEquals("mines", scores.get(1).getGame());
    assertEquals("Jaro", scores.get(1).getPlayer());
    assertEquals(120, scores.get(1).getPoints());

    assertEquals("mines", scores.get(2).getGame());
    assertEquals("Michal", scores.get(2).getPlayer());
    assertEquals(100, scores.get(2).getPoints());
  }

  private static final ScoreService scoreService = new ScoreServiceJDBC();
}
