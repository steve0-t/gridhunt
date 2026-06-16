package gamestudio.game.gridhunt.core.tile;

public enum TileType {
    GROUND('_', true),
    REVEALED_HOLE(' ', true),
    HIDDEN_HOLE('#', true),
    UNEXPLORED('#', true);

    TileType(char type, boolean walkable) {
        this.tileTypeChar = type;
        this.walkable = walkable;
    }

    public boolean getWalkable() {
        return walkable;
    }

    public char getTileTypeChar() {
        return tileTypeChar;
    }

    private final boolean walkable;
    private final char tileTypeChar;
}
