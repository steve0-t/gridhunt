package gamestudio.game.gridhunt.core.actor;

public class Health {
  public Health(int initialValue, int maxValue) {
    maxHealth = maxValue;
    hp = initialValue;
    healthDrained = 0;
  }

  public Health(int valueForBoth) {
    this(valueForBoth, valueForBoth);
  }

  public boolean isDrained() {
    return hp <= 0;
  }

  public int getValue() {
    return hp;
  }

  public void refill(int amount) {
    if (hp > 0) hp += amount;
    if (hp >= maxHealth) restore();
  }

  public void restore() {
    hp = maxHealth;
  }

  public void drain(int amount) {
    if (hp > 0) hp -= amount;
    if (hp <= 0 && healthDrained == 0) exhaust();
  }

  public void exhaust() {
    hp = 0;
    healthDrained = 1;
  }

  private int hp;
  private int healthDrained;
  private final int maxHealth;
}
