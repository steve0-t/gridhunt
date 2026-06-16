package gamestudio.game.gridhunt.core.weapons;

import gamestudio.game.gridhunt.core.Armed;

public abstract class Weapon {
  public Weapon(int damage) {
    this.damage = damage;
  }

  public void setUsingActor(Armed actor) {
    this.actor = actor;
  }

  public void increaseDamage(int value) {
    damage += value;
  }

  public void setDamage(int value) {
    damage = value;
  }

  public int getDamage() {
    return damage;
  }

  private Armed actor;
  private int damage;
}
