package gamestudio.game.gridhunt.core;

public enum HintType {
    MONSTER_NEAR("Stench"),
    HOLE_NEAR("Breeze"),
    BAT_NEAR("Flapping of wings");

    HintType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private final String value;
}
