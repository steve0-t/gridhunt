package gamestudio.service;

import gamestudio.entity.SaveData;
import gamestudio.game.gridhunt.consoleui.RenderMode;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.World;

public interface SaveService {
  void saveGame(RenderMode renderMode, World world, Scene activeScene, GameMap gameMap);

  SaveData loadGame();
}
