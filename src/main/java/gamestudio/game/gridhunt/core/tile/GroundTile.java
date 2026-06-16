package gamestudio.game.gridhunt.core.tile;

import java.util.ArrayList;
import java.util.List;
import gamestudio.game.gridhunt.core.HintEmitter;
import gamestudio.game.gridhunt.core.HintType;
import gamestudio.game.gridhunt.core.actor.Actor;

public class GroundTile extends Tile {
  public GroundTile(int x, int y) {
    super(x, y);
  }

  @Override
  public boolean isWalkable() {
    return true;
  }

  @Override
  public char getTileChar() {
    return '_';
  }

  @Override
  public List<HintType> getHints() {
    List<HintType> hints = new ArrayList<>();
    Actor actor = getFirstActor();
    if (actor instanceof HintEmitter emitter) hints.add(emitter.getHint());
    return hints;
  }
}
