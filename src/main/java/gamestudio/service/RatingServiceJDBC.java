package gamestudio.service;

import java.sql.*;
import gamestudio.entity.Rating;

public class RatingServiceJDBC implements RatingService {
  @Override
  public void setRating(Rating rating) {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(SET_RATING)) {
      statement.setString(1, rating.getPlayer());
      statement.setString(2, rating.getGame());
      statement.setInt(3, rating.getPoints());
      statement.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));

      statement.execute();
    } catch (SQLException e) {
      throw new RatingException("Problem setting rating ", e);
    }
  }

  @Override
  public int getAverageRating(String game) {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(GET_AVERAGE_RATING)) {
      statement.setString(1, game);
      try (ResultSet rs = statement.executeQuery()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    } catch (SQLException e) {
      throw new RatingException("Problem getting average rating ", e);
    }
  }

  @Override
  public int getRating(String game, String player) {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(GET_RATING)) {
      statement.setString(1, game);
      statement.setString(2, player);
      try (ResultSet rs = statement.executeQuery()) {
        return rs.next() ? rs.getInt(1) : 0;
      }
    } catch (SQLException e) {
      throw new RatingException("Problem getting rating ", e);
    }
  }

  @Override
  public void reset() {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement()) {
      statement.executeUpdate(DELETE);
    } catch (SQLException e) {
      throw new ScoreException("Problem deleting rating ", e);
    }
  }

  public static final String URL = "jdbc:postgresql://localhost/gamestudio";
  public static final String USER = "postgres";
  public static final String PASSWORD = "";

  public static final String SET_RATING =
      """
      INSERT INTO rating (player, game, rating, ratedOn)
      VALUES (?, ?, ?, ?)
      ON CONFLICT (player, game)
      DO UPDATE SET rating = EXCLUDED.rating, ratedOn = EXCLUDED.ratedOn
      """;

  public static final String GET_AVERAGE_RATING = "SELECT AVG(rating) FROM rating WHERE game = ?";

  public static final String GET_RATING = "SELECT rating FROM rating WHERE game = ? AND player = ?";

  public static final String DELETE = "DELETE FROM rating";
}
