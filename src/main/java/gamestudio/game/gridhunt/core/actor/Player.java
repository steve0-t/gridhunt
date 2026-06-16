package gamestudio.game.gridhunt.core.actor;

import gamestudio.game.gridhunt.core.Armed;
import gamestudio.game.gridhunt.core.Direction;
import gamestudio.game.gridhunt.core.weapons.Hands;
import gamestudio.game.gridhunt.core.weapons.Weapon;

public class Player extends Actor implements Movable, Armed, Alive {
    public Player(char animation) {
        this(animation, new Hands(5));
    }

    public Player(char animation, Weapon weapon) {
        super(animation);
        this.weapon = weapon;
        currentDirection = Direction.NONE;
        shootingDirection = Direction.NONE;
        health = new Health(100);
    }

    @Override
    public Direction getShootingDirection() {
        return shootingDirection;
    }

    @Override
    public void shoot(Direction dir) {
        shootingDirection = dir;
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void requestMovement(Direction dir) {
        currentDirection = dir;
    }

    @Override
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    private Health health;
    private Weapon weapon;
    private Direction currentDirection;
    private Direction shootingDirection;
}
