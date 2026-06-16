package gamestudio.game.gridhunt.core.tile;

import java.util.ArrayList;
import java.util.List;
import gamestudio.game.gridhunt.core.HintType;

public class HoleTile extends Tile {
  public HoleTile(int x, int y) {
    super(x, y);
  }

  @Override
  public boolean isWalkable() {
    return false;
  }

  @Override
  public char getTileChar() {
    return ' ';
  }

  @Override
  public List<HintType> getHints() {
    List<HintType> hints = new ArrayList<>(1);
    hints.add(HintType.HOLE_NEAR);
    return hints;
  }
}
