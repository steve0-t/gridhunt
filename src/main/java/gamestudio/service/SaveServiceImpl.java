package gamestudio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import gamestudio.entity.SaveData;
import gamestudio.game.gridhunt.consoleui.RenderMode;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.World;

public class SaveServiceImpl implements SaveService {
  @Override
  public void saveGame(RenderMode renderMode, World world, Scene activeScene, GameMap gameMap) {
    SaveData data = new SaveData();

    data.setRenderMode(renderMode);
    data.setWorld(world);
    data.setActiveScene(activeScene);
    data.setGameMap(gameMap);

    try {
      mapper.writeValue(new File(SAVE_FILE), data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public SaveData loadGame() {
    try {
      return mapper.readValue(new File(SAVE_FILE), SaveData.class);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static final String SAVE_FILE = "savegame.json";

  private final ObjectMapper mapper = new ObjectMapper();
}
