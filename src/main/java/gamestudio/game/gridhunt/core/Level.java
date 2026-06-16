package gamestudio.game.gridhunt.core;

import gamestudio.game.gridhunt.core.actor.Bat;
import gamestudio.game.gridhunt.core.actor.Wumpus;
import gamestudio.game.gridhunt.core.tile.HoleTile;

public class Level {
  public Level() {
    this(100);
  }

  public Level(int maxActors) {
    scenes = new Container<>(new Scene[10]);
    scenes.initElement(0, new Scene(maxActors));
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void populateScene() {
    Scene scene = getActiveScene();

    if (getActiveGameMap() == null) scene.generateRandomMap(difficulty);

    int holeCount = getActiveGameMap().getMapSize() / 10;
    for (int i = 0; i < holeCount; i++) {
      int x, y;
      do {
        x = getActiveGameMap().generateX();
        y = getActiveGameMap().generateY();
      } while (getActiveGameMap().isTileOccupied(x, y)
          || getActiveGameMap().getTile(x, y) instanceof HoleTile
          || scene.isNearPlayer(x, y));
      getActiveGameMap().changeTile(new HoleTile(x, y), x, y);
    }

    // System.out.println("difficulty: " + difficulty);
    int wumpusCount = Math.round((float) (difficulty.getEnemyCount()) * 0.66f);
    // System.out.println("wumpus count: " + wumpusCount);
    for (int i = 0; i < wumpusCount; i++) {
      scene.addActor(new Wumpus('W'));
    }

    int batCount = Math.round((float) (difficulty.getEnemyCount()) * 0.34f);
    // System.out.println("bat count: " + batCount);
    for (int i = 0; i < batCount; i++) scene.addActor(new Bat('B'));
    getActiveGameMap().setTileNeighbours();
  }

  public GameMap getActiveGameMap() {
    return scenes.getActive().getGameMap();
  }

  public Scene getActiveScene() {
    return scenes.getActive();
  }

  private Difficulty difficulty;
  private final Container<Scene> scenes;
}
