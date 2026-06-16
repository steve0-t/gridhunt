package gamestudio.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import gamestudio.entity.Comment;

public class CommentServiceJDBC implements CommentService {
  @Override
  public void addComment(Comment comment) throws CommentException {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(INSERT)) {
      statement.setString(1, comment.getPlayer());
      statement.setString(2, comment.getGame());
      statement.setString(3, comment.getComment());
      statement.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new CommentException("Problem inserting comment ", e);
    }
  }

  @Override
  public List<Comment> getComments(String game) throws CommentException {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement statement = connection.prepareStatement(SELECT)) {

      statement.setString(1, game);

      try (ResultSet rs = statement.executeQuery()) {
        List<Comment> comments = new ArrayList<>();
        while (rs.next()) {
          // Access columns by their names rather than indices
          comments.add(
              new Comment(
                  rs.getString("player"),
                  rs.getString("game"),
                  rs.getString("comment"),
                  rs.getTimestamp("commentedOn")));
        }
        return comments;
      }
    } catch (SQLException e) {
      throw new CommentException("Problem selecting comment ", e);
    }
  }

  @Override
  public void reset() throws CommentException {
    try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement(); ) {
      statement.executeUpdate(DELETE);
    } catch (SQLException e) {
      throw new CommentException("Problem deleting comment ", e);
    }
  }

  public static final String URL = "jdbc:postgresql://localhost/gamestudio";
  public static final String USER = "postgres";
  public static final String PASSWORD = "";
  public static final String SELECT =
      "SELECT player, game, comment, commentedOn FROM comment WHERE game = ? LIMIT 10";
  public static final String DELETE = "DELETE FROM comment";
  public static final String INSERT =
      "INSERT INTO comment (player, game, comment, commentedOn) VALUES (?, ?, ?, ?)";
}
