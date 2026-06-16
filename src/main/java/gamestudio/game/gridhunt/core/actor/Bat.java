package gamestudio.game.gridhunt.core.actor;

import gamestudio.game.gridhunt.core.HintEmitter;
import gamestudio.game.gridhunt.core.HintType;

public class Bat extends Actor implements Enemy, Alive, HintEmitter {
    public Bat(char animation) {
        super(animation);
        health = new Health(50);
    }

    @Override
    public HintType getHint() { return HintType.BAT_NEAR; }

    @Override
    public Health getHealth() {
        return health;
    }

    private Health health;
}
