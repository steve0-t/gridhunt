package gamestudio.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.*;
import gamestudio.entity.Comment;

public class CommentServiceTest {
  @AfterEach
  public void cleanup() throws SQLException {
    commentService.reset();
  }

  @Test
  public void testAddComment() throws SQLException {
    Comment comment = new Comment("Jaro", "mines", "Great game!", new Date());
    commentService.addComment(comment);

    List<Comment> comments = commentService.getComments("mines");
    assertEquals(1, comments.size());
    assertEquals("mines", comments.get(0).getGame());
    assertEquals("Jaro", comments.get(0).getPlayer());
    assertEquals("Great game!", comments.get(0).getComment());
  }

  @Test
  public void testGetComents() throws SQLException {
    commentService.reset();

    Date date = new Date();
    commentService.addComment(new Comment("Jaro", "mines", "Nice game!", date));
    commentService.addComment(new Comment("Katka", "mines", "Loved it!", date));
    commentService.addComment(new Comment("Zuzka", "tiles", "So much fun!", date));

    List<Comment> comments = commentService.getComments("mines");

    assertEquals(2, comments.size());

    assertEquals("mines", comments.get(0).getGame());
    assertEquals("Jaro", comments.get(0).getPlayer());
    assertEquals("Nice game!", comments.get(0).getComment());

    assertEquals("mines", comments.get(1).getGame());
    assertEquals("Katka", comments.get(1).getPlayer());
    assertEquals("Loved it!", comments.get(1).getComment());
  }

  @Test
  public void testAddMultipleComments() throws SQLException {
    commentService.reset();

    Date date = new Date();
    commentService.addComment(new Comment("Jaro", "mines", "Great game!", date));
    commentService.addComment(new Comment("Katka", "mines", "Really good!", date));
    commentService.addComment(new Comment("Zuzka", "mines", "Had fun!", date));

    List<Comment> comments = commentService.getComments("mines");

    assertEquals(3, comments.size());

    assertEquals("mines", comments.get(0).getGame());
    assertEquals("Jaro", comments.get(0).getPlayer());
    assertEquals("Great game!", comments.get(0).getComment());

    assertEquals("mines", comments.get(1).getGame());
    assertEquals("Katka", comments.get(1).getPlayer());
    assertEquals("Really good!", comments.get(1).getComment());

    assertEquals("mines", comments.get(2).getGame());
    assertEquals("Zuzka", comments.get(2).getPlayer());
    assertEquals("Had fun!", comments.get(2).getComment());
  }

  private final CommentService commentService = new CommentServiceJDBC();
}
