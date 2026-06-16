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
@NamedQuery(
    name = "Rating.getAverageRating",
    query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery(
    name = "Rating.getRating",
    query = "SELECT r.rating FROM Rating r WHERE r.game = :game AND r.player = :player")
@NamedQuery(name = "Rating.resetRating", query = "DELETE FROM Rating")
@NamedNativeQuery(
    name = "Rating.setRating",
    query =
"""
      INSERT INTO rating (player, game, rating, rated_on)
      VALUES (:player, :game, :rating, :ratedOn)
      ON CONFLICT (player, game)
      DO UPDATE SET rating = EXCLUDED.rating, rated_on = EXCLUDED.rated_on
""")
@Table(
    uniqueConstraints = {
      @UniqueConstraint(
          name = "rating_unique_constraint",
          columnNames = {"player", "game"})
    })
public class Rating {
  public Rating(String game, String player, int rating, Date ratedOn) {
    this.game = game;
    this.player = player;
    this.rating = rating;
    this.ratedOn = ratedOn;
  }

  public Rating() {}

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
    return rating;
  }

  public void setPoints(int points) {
    this.rating = points;
  }

  public Date getRatedOn() {
    return ratedOn;
  }

  public void setRatedOn(Date playedOn) {
    this.ratedOn = playedOn;
  }

  public int getIdent() {
    return ident;
  }

  public void setIdent(int ident) {
    this.ident = ident;
  }

  private String game;
  private String player;
  private int rating;
  private Date ratedOn;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int ident;
}
