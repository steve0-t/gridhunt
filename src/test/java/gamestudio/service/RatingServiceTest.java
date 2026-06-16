package gamestudio.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Date;
import org.junit.jupiter.api.*;
import gamestudio.entity.Rating;

public class RatingServiceTest {
  @BeforeAll
  public static void prepare() throws SQLException {
    ratingService.reset();
  }

  @AfterEach
  public void cleanup() throws SQLException {
    ratingService.reset();
  }

  @Test
  public void testAddValidRating() throws SQLException {
    ratingService.setRating(new Rating("mines", "jaro", 5, new Date()));
    int rating = ratingService.getRating("mines", "jaro");
    assertEquals(5, rating);
  }

  @Test
  public void testDuplicateRating() throws SQLException {
    ratingService.setRating(new Rating("mines", "jaro", 5, new Date()));
    ratingService.setRating(new Rating("mines", "jaro", 3, new Date()));
    int rating = ratingService.getRating("mines", "jaro");
    assertEquals(3, rating);
  }

  // @Test
  // public void testAddInvalidRating() throws SQLException {
  //   ratingService.setRating(new Rating("mines", "jaro", 9, new Date()));
  //   assertThrows(RatingException.class, () -> ratingService.getRating("mines", "jaro"));
  // }

  @Test
  public void testGetAverageRating() throws SQLException {
    ratingService.setRating(new Rating("mines", "jaro", 4, new Date()));
    ratingService.setRating(new Rating("mines", "jan", 4, new Date()));
    ratingService.setRating(new Rating("mines", "filip", 4, new Date()));
    ratingService.setRating(new Rating("mines", "jozef", 4, new Date()));

    assertEquals(4, ratingService.getAverageRating("mines"));
  }

  private static final RatingService ratingService = new RatingServiceJDBC();
}
