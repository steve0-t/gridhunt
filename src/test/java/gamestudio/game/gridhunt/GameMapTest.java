package gamestudio.game.gridhunt;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import gamestudio.game.gridhunt.core.GameMap;
import gamestudio.game.gridhunt.core.actor.Player;
import gamestudio.game.gridhunt.core.tile.HoleTile;
import gamestudio.game.gridhunt.core.tile.Tile;

class GameMapTest {
  @BeforeEach
  void setUp() {
    gameMap = new GameMap(5, 5);
  }

  @Test
  void testGenerateMap() {
    assertEquals(25, gameMap.getMapSize());
  }

  @Test
  void testGenerateX() {
    int x = gameMap.generateX();
    assertTrue(x >= 0 && x < gameMap.getWidth());
  }

  @Test
  void testGenerateY() {
    int y = gameMap.generateY();
    assertTrue(y >= 0 && y < gameMap.getHeight());
  }

  @Test
  void testBadInput() {
    assertTrue(gameMap.badInput(-1, 0));
    assertTrue(gameMap.badInput(0, -1));
    assertTrue(gameMap.badInput(5, 0));
    assertTrue(gameMap.badInput(0, 5));
    assertFalse(gameMap.badInput(2, 2));
  }

  @Test
  void testGetTile() {
    Tile tile = gameMap.getTile(2, 2);
    assertNotNull(tile);
  }

  @Test
  void testTileRetainsOccupantsWhenChangingType() {
    gameMap.getTile(2, 2).addOccupant(new Player('X'));

    gameMap.changeTile(new HoleTile(2, 2), 2, 2);

    Tile newTile = gameMap.getTile(2, 2);
    assertTrue(newTile.isOccupied());
  }

  @Test
  void testIsValidPosition() {
    assertTrue(gameMap.isValidPosition(2, 2));
    assertFalse(gameMap.isValidPosition(5, 5));
  }

  @Test
  void testDespawnActor() {
    Player player = new Player('X');

    gameMap.spawnActor(player, 2, 2);
    gameMap.despawnActor(player);
    Tile tile = gameMap.getTile(2, 2);

    assertFalse(tile.isOccupied());
    assertEquals(-1, player.getX());
    assertEquals(-1, player.getY());
  }

  @Test
  void testMoveActorToValidPosition() {
    Player player = new Player('X');
    gameMap.spawnActor(player, 2, 2);
    gameMap.moveActor(player, 3, 3);

    Tile oldTile = gameMap.getTile(2, 2);
    Tile newTile = gameMap.getTile(3, 3);

    assertFalse(oldTile.isOccupied());
    assertTrue(newTile.isOccupied());
  }

  @Test
  void testMoveActorToInvalidPosition() {
    Player player = new Player('X');
    gameMap.spawnActor(player, 2, 2);
    gameMap.moveActor(player, 5, 5);

    Tile oldTile = gameMap.getTile(2, 2);

    assertTrue(oldTile.isOccupied());
  }

  @Test
  void testTileNeighbours() {
    gameMap.setTileNeighbours();
    Tile[] neighbours = gameMap.getTile(2, 2).getNeighbours();
    assertEquals(8, neighbours.length);
  }

  private GameMap gameMap;
}
