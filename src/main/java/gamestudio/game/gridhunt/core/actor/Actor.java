package gamestudio.game.gridhunt.core.actor;

public abstract class Actor implements AbstractActor {
  public Actor(char animation) {
    x = 0;
    y = 0;
    this.animation = animation;
    this.index = -1;
  }

  @Override
  public void setAnimation(char newAnimation) {
    animation = newAnimation;
  }

  @Override
  public char getAnimation() {
    return animation;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public void setIndex(int newIndex) {
    this.index = newIndex;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  private int x;
  private int y;
  private int index;
  private char animation;
}
