package gamestudio.server.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gamestudio.entity.Rating;
import gamestudio.service.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingServiceRest {
  @GetMapping("/{game}/average")
  public int getAverageRating(@PathVariable String game) {
    // System.out.println("game: " + game);
    int avg = ratingService.getAverageRating(game);
    // System.out.println("average: " + avg);
    return avg;
  }

  @GetMapping("/{game}/player/{player}")
  public int getRating(@PathVariable String game, @PathVariable String player) {
    // System.out.println("game: " + game);
    // System.out.println("player: " + player);
    return ratingService.getRating(game, player);
  }

  @PostMapping
  public void setRating(@RequestBody Rating rating) {
    System.out.println("points: " + rating.getPoints());
    System.out.println("player: " + rating.getPlayer());
    System.out.println("game: " + rating.getGame());
    System.out.println("rated on: " + rating.getRatedOn());
    ratingService.setRating(rating);
  }

  @Autowired private RatingService ratingService;
}
