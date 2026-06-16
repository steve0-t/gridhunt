package gamestudio.game.gridhunt.core.actor;

public interface AbstractActor {
  int getIndex();

  void setIndex(int newIndex);

  int getX();

  int getY();

  void setX(int newX);

  void setY(int newY);

  void setAnimation(char newAnimation);

  char getAnimation();
}
