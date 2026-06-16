package gamestudio.game.gridhunt;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gamestudio.game.gridhunt.core.actor.Enemy;
import gamestudio.game.gridhunt.core.actor.Player;
import gamestudio.game.gridhunt.core.actor.Wumpus;

public class WorldTest {
  @BeforeEach
  public void init() {
    world = new World();
    world.generateDefault();
  }

  @Test
  public void testDifficultyChanges() {
    world.setDifficulty(Difficulty.EASY);
    assertSame(Difficulty.EASY, world.getDifficulty());

    world.setDifficulty(Difficulty.MEDIUM);
    assertSame(Difficulty.MEDIUM, world.getDifficulty());

    world.setDifficulty(Difficulty.HARD);
    assertSame(Difficulty.HARD, world.getDifficulty());
  }

  @Test
  public void testWhetherGameCanBeLost() {
    Level level = world.getActiveLevel();
    Player player = level.getActiveScene().getActivePlayer();
    GameMap gameMap = level.getActiveGameMap();

    Wumpus wumpus = null;
    for (int y = 0; y < gameMap.getHeight(); y++) {
      for (int x = 0; x < gameMap.getWidth(); x++) {
        if (gameMap.getTile(x, y).getFirstActor() instanceof Wumpus) {
          wumpus = (Wumpus) gameMap.getTile(x, y).getFirstActor();
          break;
        }
      }
      if (wumpus != null) break;
    }
    assertNotNull(wumpus, "Could not find wumpus, map was not correctly populated");
    gameMap.moveActor(player, wumpus.getX(), wumpus.getY());
    world.update();

    assertEquals(GameState.FAILED, world.getGameState());
  }

  @Test
  public void testWhetherGameCanBeWon() {
    Level level = world.getActiveLevel();
    GameMap gameMap = level.getActiveGameMap();
    Scene scene = level.getActiveScene();

    for (int y = 0; y < gameMap.getHeight(); y++) {
      for (int x = 0; x < gameMap.getWidth(); x++) {
        if (gameMap.getTile(x, y).getFirstActor() instanceof Enemy) {
          scene.removeActor(gameMap.getTile(x, y).getFirstActor());
        }
      }
    }
    world.update();
    assertEquals(GameState.WON, world.getGameState());
  }

  @Test
  public void testWorldReset() {
    world.setDifficulty(Difficulty.HARD);
    Difficulty oldDifficulty = world.getDifficulty();
    world.setGameState(GameState.FAILED);
    GameState gameState = world.getGameState();

    world.reset();

    assertNotNull(world.getActiveLevel());

    assertNotEquals(oldDifficulty, world.getDifficulty());
    assertNotEquals(gameState, world.getGameState());

    assertEquals(Difficulty.EASY, world.getDifficulty());
    assertEquals(GameState.PLAYING, world.getGameState());
  }

  private World world;
}
