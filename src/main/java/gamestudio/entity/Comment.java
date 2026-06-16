package gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import java.util.Date;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(
    name = "Comment.getComments",
    query = "SELECT c FROM Comment c WHERE c.game = :game ORDER BY c.commentedOn DESC")
@NamedQuery(name = "Comment.resetComments", query = "DELETE FROM Comment")
@NamedNativeQuery(
    name = "Comment.addComment",
    query =
        """
            INSERT INTO comment (player, game, comment, commented_on)
            VALUES (:player, :game, :comment, :commentedOn)
        """)
public class Comment {
  public Comment(String player, String game, String comment, Date commentedOn) {
    this.player = player;
    this.game = game;
    this.comment = comment;
    this.commentedOn = commentedOn;
  }

  public Comment() {}

  @Override
  public String toString() {
    return "Score{"
        + "game='"
        + game
        + '\''
        + ", player='"
        + player
        + '\''
        + ", points="
        + comment
        + ", playedOn="
        + commentedOn
        + '}';
  }

  public void setGame(String game) {
    this.game = game;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public void setCommentedOn(Date commentedOn) {
    this.commentedOn = commentedOn;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getGame() {
    return game;
  }

  public String getPlayer() {
    return player;
  }

  public Date getCommentedOn() {
    return commentedOn;
  }

  public String getComment() {
    return comment;
  }

  public int getIdent() {
    return ident;
  }

  public void setIdent(int ident) {
    this.ident = ident;
  }

  private String player;
  private String game;
  private String comment;
  private Date commentedOn;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ident;
}
