package gamestudio.game.gridhunt.core;

import java.util.Arrays;
import java.util.Random;
import gamestudio.game.gridhunt.core.actor.Actor;
import gamestudio.game.gridhunt.core.tile.GroundTile;
import gamestudio.game.gridhunt.core.tile.Tile;

public class GameMap {
  public GameMap(int height, int width) {
    this.height = height;
    this.width = width;
    tiles = new Tile[height * width];
    generateMap();
    setTileNeighbours();
  }

  public int generateX() {
    return random.nextInt(0, width);
  }

  public int generateY() {
    return random.nextInt(0, height);
  }

  public boolean badInput(int x, int y) {
    return x < 0 || x >= width || y < 0 || y >= height;
  }

  public int getMapSize() {
    return width * height;
  }

  public boolean isTileOccupied(int x, int y) {
    if (badInput(x, y)) return false;
    Tile tile = getTile(x, y);
    return tile != null && tile.isOccupied();
  }

  public void changeTile(Tile newTile, int x, int y) {
    if (!isValidPosition(x, y)) return;
    Tile oldTile = tiles[y * width + x];
    newTile.setNeighbours(oldTile.getNeighbours());
    newTile.setExplored(oldTile.isExplored());
    newTile.addOccupants(oldTile.getOccupants());
    tiles[y * width + x] = newTile;
  }

  public Tile getTile(int x, int y) {
    if (!isValidPosition(x, y)) return null;
    return tiles[y * width + x];
  }

  public boolean isValidPosition(int x, int y) {
    return x < width && x >= 0 && y < height && y >= 0;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void spawnActors(Actor[] actors, int actorCount) {
    for (int i = 0; i < actorCount; i++) spawnActor(actors[i], actors[i].getX(), actors[i].getY());
  }

  public void spawnActor(Actor actor, int x, int y) {
    actor.setPosition(x, y);
    tiles[y * width + x].addOccupant(actor);
  }

  public void despawnActor(Actor actor) {
    int y = actor.getY();
    int x = actor.getX();
    tiles[y * width + x].removeOccupant(actor);
    actor.setPosition(-1, -1);
  }

  public void moveActor(Actor actor, Direction dir) {
    int oldX = actor.getX();
    int oldY = actor.getY();
    int newX = oldX + dir.getDx();
    int newY = oldY + dir.getDy();
    if (!badInput(newX, newY)) {
      actor.setPosition(newX, newY);
      tiles[newY * width + newX].addOccupant(actor);
      tiles[oldY * width + oldX].removeOccupant(actor);
    }
  }

  public void moveActor(Actor actor, int x, int y) {
    int oldX = actor.getX();
    int oldY = actor.getY();
    if (!badInput(x, y)) {
      actor.setPosition(x, y);
      tiles[y * width + x].addOccupant(actor);
      tiles[oldY * width + oldX].removeOccupant(actor);
    }
  }

  public void generateMap() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) tiles[y * width + x] = new GroundTile(x, y);
    }
  }

  public void setTileNeighbours() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Tile tile = tiles[y * width + x];
        Tile[] neighbours = new Tile[8];
        int idx = 0;

        for (int dy = -1; dy <= 1; dy++) {
          for (int dx = -1; dx <= 1; dx++) {
            if (dx == 0 && dy == 0) continue;
            int nx = x + dx;
            int ny = y + dy;
            neighbours[idx++] = inBounds(nx, ny) ? tiles[ny * width + nx] : null;
          }
        }

        tile.setNeighbours(Arrays.copyOf(neighbours, idx));
      }
    }
  }

  private boolean inBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  private final int height;
  private final int width;
  private final Tile[] tiles;

  private static final Random random;

  static {
    random = new Random();
  }
}
