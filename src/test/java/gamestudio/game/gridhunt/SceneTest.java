package gamestudio.game.gridhunt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import gamestudio.game.gridhunt.core.Difficulty;
import gamestudio.game.gridhunt.core.Scene;
import gamestudio.game.gridhunt.core.actor.Player;

public class SceneTest {
  @BeforeEach
  public void init() {
    activeScene = new Scene(50);
    activeScene.generateRandomMap(Difficulty.HARD);
  }

  @Test
  public void testPlayerAddition() {
    int expected = 0;
    for (int y = 0; y < 2; y++) {
      for (int x = 0; x < 10; x++) {
        activeScene.addActor(new Player('*'), x, y);
        expected++;
      }
    }
    assertEquals(expected, activeScene.getActorCount());
  }

  private Scene activeScene;
}
