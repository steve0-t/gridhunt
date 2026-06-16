package gamestudio.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import gamestudio.entity.Score;

public class ScoreServiceJDBC implements ScoreService {
  public static final String URL = "jdbc:postgresql://localhost/gamestudio";
  public static final String USER = "postgres";
  public static final String PASSWORD = "";
  public static final String SELECT =
      "SELECT game, player, points, playedOn FROM score WHERE game = ? ORDER BY points DESC LIMIT"
          + " 10";
  public static final String DELETE = "DELETE FROM score";
  public static final String INSERT =
      "INSERT INTO score (game, player, points, playedOn) VALUES (?, ?, ?, ?)";

  @Override
  public void addScore(Score score) {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(INSERT)) {
      statement.setString(1, score.getGame());
      statement.setString(2, score.getPlayer());
      statement.setInt(3, score.getPoints());
      statement.setTimestamp(4, new Timestamp(score.getPlayedOn().getTime()));
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new ScoreException("Problem inserting score", e);
    }
  }

  @Override
  public List<Score> getTopScores(String game) {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(SELECT); ) {
      statement.setString(1, game);
      try (ResultSet rs = statement.executeQuery()) {
        List<Score> scores = new ArrayList<>();
        while (rs.next())
          scores.add(
              new Score(
                  rs.getString("game"),
                  rs.getString("player"),
                  rs.getInt("points"),
                  rs.getTimestamp("playedOn")));
        return scores;
      }
    } catch (SQLException e) {
      throw new ScoreException("Problem selecting score", e);
    }
  }

  @Override
  public void reset() {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement(); ) {
      statement.executeUpdate(DELETE);
    } catch (SQLException e) {
      throw new ScoreException("Problem deleting score", e);
    }
  }
}
