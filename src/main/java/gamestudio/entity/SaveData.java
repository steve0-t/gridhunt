package gamestudio.entity;

import gamestudio.game.gridhunt.consoleui.RenderMode;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.World;

public class SaveData {
  public SaveData() {}

  public RenderMode getRenderMode() {
    return renderMode;
  }

  public void setRenderMode(RenderMode renderMode) {
    this.renderMode = renderMode;
  }

  public World getWorld() {
    return world;
  }

  public void setWorld(World world) {
    this.world = world;
  }

  public Scene getActiveScene() {
    return activeScene;
  }

  public void setActiveScene(Scene activeScene) {
    this.activeScene = activeScene;
  }

  public GameMap getGameMap() {
    return gameMap;
  }

  public void setGameMap(GameMap gameMap) {
    this.gameMap = gameMap;
  }

  private RenderMode renderMode;
  private World world;
  private Scene activeScene;
  private GameMap gameMap;
}
