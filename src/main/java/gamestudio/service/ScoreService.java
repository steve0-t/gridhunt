package gamestudio.service;

import java.util.List;
import gamestudio.entity.Score;

public interface ScoreService {
  void addScore(Score score) throws ScoreException;

  List<Score> getTopScores(String game) throws ScoreException;

  void reset() throws ScoreException;
}
