package gamestudio.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import gamestudio.entity.Score;

public class ScoreServiceRestClient implements ScoreService {
  @Override
  public void addScore(Score score) {
    restTemplate.postForEntity(url, score, Score.class);
  }

  @Override
  public List<Score> getTopScores(String gameName) {
    return Arrays.asList(restTemplate.getForEntity(url + "/" + gameName, Score[].class).getBody());
  }

  @Override
  public void reset() {
    throw new UnsupportedOperationException("Not supported via web service");
  }

  private final String url = "http://localhost:8080/api/score";

  @Autowired private RestTemplate restTemplate;
}
