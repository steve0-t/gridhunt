package gamestudio.game.gridhunt.core;

public enum Difficulty {
  TESTING(10, 10, 1),
  EASY(10, 10, 20),
  MEDIUM(20, 20, 50),
  HARD(30, 30, 100);

  public int getEnemyCount() {
    return enemyCount;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  Difficulty(int width, int height, int enemyCount) {
    this.width = width;
    this.height = height;
    this.enemyCount = enemyCount;
  }

  private final int enemyCount;
  private final int width;
  private final int height;
}
