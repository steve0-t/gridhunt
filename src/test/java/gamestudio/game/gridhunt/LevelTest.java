package gamestudio.game.gridhunt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import gamestudio.game.gridhunt.core.Difficulty;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.Level;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.actor.Player;
import gamestudio.game.gridhunt.core.tile.HoleTile;

public class LevelTest {
  @BeforeEach
  public void init() {
    activeLevel = new Level(maxActors);
    activeLevel.getActiveScene().setActivePlayer(new Player('*'));
  }

  @RepeatedTest(value = 3)
  public void testEnemyCount(RepetitionInfo repetitionInfo) {
    Difficulty difficulty;
    switch (repetitionInfo.getCurrentRepetition()) {
      case 2:
        difficulty = Difficulty.MEDIUM;
        break;
      case 3:
        difficulty = Difficulty.HARD;
        break;
      default:
        difficulty = Difficulty.EASY;
        break;
    }
    activeLevel.getActiveScene().generateRandomMap(difficulty);
    activeLevel.setDifficulty(difficulty);
    activeLevel.populateScene();
    assertEquals(difficulty.getEnemyCount(), activeLevel.getActiveScene().getEnemyCount());
  }

  @RepeatedTest(value = 3)
  public void testHoleCount(RepetitionInfo info) {
    Difficulty difficulty;
    switch (info.getCurrentRepetition()) {
      case 2:
        difficulty = Difficulty.MEDIUM;
        break;
      case 3:
        difficulty = Difficulty.HARD;
        break;
      default:
        difficulty = Difficulty.EASY;
        break;
    }
    activeLevel.getActiveScene().generateRandomMap(difficulty);
    activeLevel.setDifficulty(difficulty);
    activeLevel.populateScene();

    GameMap gameMap = activeLevel.getActiveGameMap();
    int expected = gameMap.getMapSize() / 10;
    int actual = 0;

    for (int y = 0; y < gameMap.getHeight(); y++) {
      for (int x = 0; x < gameMap.getWidth(); x++) {
        if (gameMap.getTile(x, y) instanceof HoleTile) actual++;
      }
    }
    assertEquals(expected, actual);
  }

  @Test
  void testPopulateGeneratesMapIfNull() {
    Level level = new Level(10);
    level.setDifficulty(Difficulty.EASY);

    Scene scene = level.getActiveScene();
    assertNull(scene.getGameMap());

    level.populateScene();

    assertNotNull(scene.getGameMap());
  }

  @Test
  void testPopulateSceneTwice() {
    Level level = new Level(50);
    level.setDifficulty(Difficulty.EASY);

    level.populateScene();
    level.populateScene(); // should not crash

    assertNotNull(level.getActiveGameMap());
  }

  @Test
  void testDifficultyAffectsEnemyCount() {
    Level levelEasy = new Level(1000);
    levelEasy.setDifficulty(Difficulty.EASY);
    levelEasy.populateScene();

    Level levelHard = new Level(1000);
    levelHard.setDifficulty(Difficulty.HARD);
    levelHard.populateScene();

    int easyCount = levelEasy.getActiveScene().getEnemyCount();
    int hardCount = levelHard.getActiveScene().getEnemyCount();

    assertTrue(hardCount > easyCount);
  }

  private final int maxActors = 500;
  private Level activeLevel;
}
