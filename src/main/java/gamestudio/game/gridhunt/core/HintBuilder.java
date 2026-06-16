package gamestudio.game.gridhunt.core;

import java.util.Arrays;

public class HintBuilder {
  public HintBuilder() {
    count = 0;
    hints = new HintType[HintType.values().length];
  }

  public HintBuilder add(HintType hint) {
    for (int i = 0; i < count; i++) {
      if (hints[i] == hint) return this;
    }
    hints[count++] = hint;
    return this;
  }

  public final HintType[] getHints() {
    return Arrays.copyOf(hints, count);
  }

  private int count;
  private final HintType[] hints;
}
