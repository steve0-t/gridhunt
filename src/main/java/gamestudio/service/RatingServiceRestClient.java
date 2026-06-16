package gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import gamestudio.entity.Rating;

public class RatingServiceRestClient implements RatingService {

  @Override
  public void setRating(Rating rating) throws RatingException {
    restTemplate.postForEntity(url, rating, Rating.class);
  }

  @Override
  public int getAverageRating(String game) throws RatingException {
    return restTemplate.getForObject(url + "/" + game + "/" + "average", Integer.class).intValue();
  }

  @Override
  public int getRating(String game, String player) throws RatingException {
    return restTemplate
        .getForObject(url + "/" + game + "/" + "player" + player, Integer.class)
        .intValue();
  }

  @Override
  public void reset() throws RatingException {
    throw new UnsupportedOperationException("Not supported via web service");
  }

  private final String url = "http://localhost:8080/api/rating";

  @Autowired private RestTemplate restTemplate;
}
