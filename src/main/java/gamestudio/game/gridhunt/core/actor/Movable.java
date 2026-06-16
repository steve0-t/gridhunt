package gamestudio.game.gridhunt.core.actor;

import gamestudio.game.gridhunt.core.Direction;

public interface Movable {
    void requestMovement(Direction dir);

    Direction getCurrentDirection();
}
