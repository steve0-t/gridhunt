package gamestudio.game.gridhunt.core;

import java.util.ArrayList;
import java.util.List;

import gamestudio.game.gridhunt.core.actor.*;
import sk.tuke.gamestudio.game.gridhunt.core.actor.*;
import gamestudio.game.gridhunt.core.tile.HoleTile;
import gamestudio.game.gridhunt.core.tile.Tile;

public class Scene {
  public Scene(int maxActors) {
    this.maxActors = maxActors;
    actors = new Actor[maxActors];
    enemyCount = 0;
  }

  public SceneResult update() {
    SceneResult res = new SceneResult();
    checkForMovement();

    res.playerDied = checkForEnemy() || checkForHole();
    if (res.playerDied == true) return res;

    getPlayerTile().setExplored(true);

    res.scoreDelta = (checkForAttack()) ? 1 : 0;
    res.hints = checkForHint();
    return res;
  }

  public Actor[] getActors() {
    return actors.clone();
  }

  public int getEnemyCount() {
    return enemyCount;
  }

  public Player getActivePlayer() {
    return activePlayer;
  }

  // public boolean isOnSameTile(Actor actor1, Actor actor2) {
  //     return actor1.getX() == actor2.getX() && actor1.getY() == actor2.getY();
  // }

  public void addActor(Actor actor) {
    Tile tile = findValidTile();
    addActor(actor, tile.getX(), tile.getY());
  }

  public void addPlayer(Player p, int x, int y) {
    addActor(p, x, y);
    gameMap.getTile(x, y).setExplored(true);
  }

  public void addActor(Actor actor, int x, int y) {
    if (actorCount >= maxActors) return;
    // System.out.println("adding " + actor + " at: " + "x = " + x + " y = " + y);
    actor.setIndex(actorCount);
    actors[actorCount++] = actor;
    if (actor instanceof Enemy) enemyCount++;
    // if (actor instanceof Player) playerCount++;
    getGameMap().spawnActor(actor, x, y);
  }

  public void resetActors() {
    while (actorCount > 0) {
      removeActor(actors[actorCount - 1]);
    }
    enemyCount = 0;
  }

  public void setActivePlayer(Player player) {
    activePlayer = player;
  }

  public void removeActor(Actor actor) {
    if (actor == null) return;
    getGameMap().despawnActor(actor);
    int idx = actor.getIndex();
    Actor last = actors[actorCount - 1];
    actors[idx] = last;
    last.setIndex(idx);
    actors[actorCount - 1] = null;
    actorCount--;
    if (actor instanceof Enemy) enemyCount--;
    // if (actor instanceof Player) playerCount--;
  }

  public boolean isNearPlayer(int x, int y) {
    return Math.abs(activePlayer.getX() - x) <= 1 && Math.abs(activePlayer.getY() - y) <= 1;
  }

  public boolean isNearPlayer(Tile tile) {
    return isNearPlayer(tile.getX(), tile.getY());
  }

  public int getActorCount() {
    return actorCount;
  }

  public GameMap getGameMap() {
    return gameMap;
  }

  public void generateRandomMap(Difficulty difficulty) {
    gameMap = new GameMap(difficulty.getHeight(), difficulty.getWidth());
  }

  private Tile findValidTile() {
    // Tile pTile = gameMap.getTile(activePlayer.getX(), activePlayer.getY());
    int x, y;
    do {
      x = gameMap.generateX();
      y = gameMap.generateY();
      // System.out.printf("Find valid tile x: %d | y: %d\n", x, y);
    } while (gameMap.isTileOccupied(x, y) || gameMap.getTile(x, y) instanceof HoleTile);
    return gameMap.getTile(x, y);
  }

  private Tile getPlayerTile() {
    return gameMap.getTile(activePlayer.getX(), activePlayer.getY());
  }

  private void checkForMovement() {
    Direction desiredDirection = activePlayer.getCurrentDirection();
    if (desiredDirection != Direction.NONE) {
      gameMap.moveActor(activePlayer, desiredDirection);
      activePlayer.requestMovement(Direction.NONE);
    }
  }

  private boolean checkForAttack() {
    Direction shootingDirection = activePlayer.getShootingDirection();
    if (shootingDirection == Direction.NONE) return false;
    activePlayer.shoot(Direction.NONE);
    // System.out.println("shooting dir: " + shootingDirection);
    int dx = shootingDirection.getDx();
    int dy = shootingDirection.getDy();
    int x = activePlayer.getX();
    int y = activePlayer.getY();
    Tile tile = gameMap.getTile(x + dx, y + dy);
    if (tile == null) return false;
    Actor target = tile.getFirstActor();
    // System.out.println("target: " + activePlayer
    // System.out.println("tile: " + tile);
    if (target instanceof Enemy) {
      removeActor(target);
      return true;
    }
    return false;
  }

  private List<HintType> checkForHint() {
    List<HintType> hints = new ArrayList<>(3);
    Tile playerTile = gameMap.getTile(activePlayer.getX(), activePlayer.getY());
    Tile[] x = playerTile.getNeighbours();
    // for (Tile n : x) System.out.println(n);

    for (Tile tile : x) {
      if (tile == null) continue;
      hints.addAll(tile.getHints());
      // System.out.println("tile: " + tile);
      // System.out.println("first: " + tile.getFirstActor());
      // System.out.println("last: " + tile.getLastActor());
      // System.out.println("hints: " + hints);
    }
    return hints;
  }

  private boolean checkForHole() {
    Health playerHealth = activePlayer.getHealth();
    if (getPlayerTile() instanceof HoleTile) playerHealth.drain(playerHealth.getValue());
    return playerHealth.isDrained();
  }

  private boolean checkForEnemy() {
    Tile tile = gameMap.getTile(activePlayer.getX(), activePlayer.getY());
    if (!tile.isOccupied()) return false;
    Actor actor = tile.getFirstActor();
    // System.out.println("first actor: " + actor);
    if (actor instanceof Wumpus) {
      Health playerHealth = activePlayer.getHealth();
      playerHealth.drain(playerHealth.getValue());
      return true;
    } else if (actor instanceof Bat) {
      batCausedMovement();
      checkForEnemy();
    }
    movedByBat = 0;
    return false;
  }

  private void batCausedMovement() {
    int x = gameMap.generateX();
    int y = gameMap.generateY();
    if (gameMap.getTile(x, y).getFirstActor() instanceof Bat) movedByBat += 1;
    if (movedByBat >= 5) {
      do {
        x = gameMap.generateX();
        y = gameMap.generateY();
      } while (gameMap.getTile(x, y).getFirstActor() instanceof Bat);
    }
    gameMap.moveActor(activePlayer, x, y);
  }

  private final int maxActors;
  private int movedByBat;
  private int enemyCount;
  private Player activePlayer;
  private final Actor[] actors;
  private int actorCount;

  private GameMap gameMap;
}
