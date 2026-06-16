package gamestudio.game.gridhunt.core.actor;

import gamestudio.game.gridhunt.core.HintEmitter;
import gamestudio.game.gridhunt.core.HintType;

public class Wumpus extends Actor implements Enemy, Alive, HintEmitter {
    public Wumpus(char animation) {
        super(animation);
        health = new Health(100);
    }

    @Override
    public HintType getHint() { return HintType.MONSTER_NEAR; }

    @Override
    public Health getHealth() {
        return health;
    }

    private Health health;
}
