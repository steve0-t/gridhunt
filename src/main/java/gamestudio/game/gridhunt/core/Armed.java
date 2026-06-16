package gamestudio.game.gridhunt.core;

import gamestudio.game.gridhunt.core.actor.AbstractActor;
import gamestudio.game.gridhunt.core.weapons.Weapon;

public interface Armed extends AbstractActor {
    void setWeapon(Weapon weapon);
    Weapon getWeapon();
    void shoot(Direction dir);
    Direction getShootingDirection();
}
