package gamestudio.game.gridhunt.core;

public enum Direction {
    NONE(0, 0),
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);
    /*
    NORTHEAST(1, 1, 315.0f),
    NORTHWEST(-1, 1, 45.0f),
    SOUTHEAST(1, -1, 225.0f),
    SOUTHWEST(-1, -1, 135.0f);
     */

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public static Direction containsDirection(int x, int y) {
        for (Direction dir : Direction.values()) {
            if (dir.dx == x && dir.dy == y) return dir;
        }
        return NONE;
    }

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    private final int dx;
    private final int dy;
}
