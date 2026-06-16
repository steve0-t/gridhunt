package gamestudio.entity;

import java.io.Serializable;
import java.util.Objects;

public class ServicesID implements Serializable {
  public ServicesID(String game, String player) {
    this.game = game;
    this.player = player;
  }

  public ServicesID() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ServicesID)) return false;
    ServicesID other = (ServicesID) o;
    return Objects.equals(game, other.game) && Objects.equals(player, other.player);
  }

  @Override
  public int hashCode() {
    return Objects.hash(game, player);
  }

  private String game;
  private String player;
}
