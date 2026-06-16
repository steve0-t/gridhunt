package gamestudio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQuery(name = "Score.resetScores", query = "DELETE FROM Score")
@NamedQuery(
    name = "Score.getTopScores",
    query =
        """
        SELECT s FROM Score s WHERE s.game = :game
        ORDER BY s.points DESC
        """)
@NamedNativeQuery(
    name = "Score.addScore",
    query =
        """
        INSERT INTO score (game, player, points, played_on)
        VALUES (:game, :player, :points, :playedOn)
        ON CONFLICT (player, game)
        DO UPDATE SET points = EXCLUDED.points, played_on = EXCLUDED.played_on
        """)
@Table(
    uniqueConstraints = {
      @UniqueConstraint(
          name = "score_unique_constraint",
          columnNames = {"player", "game"})
    })
public class Score {
  public Score(String game, String player, int points, Date playedOn) {
    this.game = game;
    this.player = player;
    this.points = points;
    this.playedOn = playedOn;
  }

  public Score() {}

  public String getGame() {
    return game;
  }

  public void setGame(String game) {
    this.game = game;
  }

  public String getPlayer() {
    return player;
  }

  public void setPlayer(String player) {
    this.player = player;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public Date getPlayedOn() {
    return playedOn;
  }

  public void setPlayedOn(Date playedOn) {
    this.playedOn = playedOn;
  }

  public int getIdent() {
    return ident;
  }

  public void setIdent(int ident) {
    this.ident = ident;
  }

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
        + points
        + ", playedOn="
        + playedOn
        + '}';
  }

  private String game;
  private String player;
  private int points;
  private Date playedOn;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ident;
}
