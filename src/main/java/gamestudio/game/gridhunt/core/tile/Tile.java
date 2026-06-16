package gamestudio.game.gridhunt.core.tile;

import java.util.*;
import gamestudio.game.gridhunt.consoleui.RenderMode;
import gamestudio.game.gridhunt.core.HintType;
import gamestudio.game.gridhunt.core.actor.Actor;

public abstract class Tile {
  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public abstract boolean isWalkable();

  public abstract char getTileChar();

  public abstract List<HintType> getHints();

  public char renderTile(RenderMode renderMode) {
    boolean visible = isExplored() || renderMode == RenderMode.REVEALED;
    char res;
    if (!visible) {
      res = '#';
    } else {
      res = getTileChar();
      if (isOccupied()) res = getLastActor().getAnimation();
    }
    return res;
  }

  public void setNeighbours(Tile[] neighbours) {
    if (neighbours.length != 8)
      throw new IllegalArgumentException("Must provide exactly 8 neighbours.");
    this.neighbours = Arrays.copyOf(neighbours, neighbours.length);
  }

  public Tile[] getNeighbours() {
    return Arrays.copyOf(neighbours, neighbours.length);
  }

  public void setExplored(boolean value) {
    explored = value;
  }

  public boolean isExplored() {
    return explored;
  }

  public boolean isOccupied() {
    return !occupyingActors.isEmpty();
  }

  public Actor getFirstActor() {
    return isOccupied() ? occupyingActors.get(0) : null;
  }

  public Actor getLastActor() {
    return isOccupied() ? occupyingActors.get(occupyingActors.size() - 1) : null;
  }

  public void addOccupants(List<Actor> actors) {
    occupyingActors.addAll(actors);
  }

  public List<Actor> getOccupants() {
    return occupyingActors;
  }

  public void addOccupant(Actor actor) {
    occupyingActors.add(actor);
  }

  public void removeOccupant(Actor actor) {
    occupyingActors.remove(actor);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  protected final int x;
  protected final int y;
  private boolean explored;
  protected final List<Actor> occupyingActors = new ArrayList<>();
  protected Tile[] neighbours = new Tile[8];
}
