package src.tools;

import java.awt.geom.Rectangle2D;

import static src.tools.Constants.Fenetre.*;

public class Methods {
    public static boolean MoveAllowed(float x, float y, float width, float height, int[][] roomData) {
        if (!Collision(x, y, roomData)) {
            if (!Collision(x + width, y + height, roomData)) {
                if (!Collision(x + width, y, roomData)) {
                    if (!Collision(x, y + height, roomData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean Collision(float x, float y, int[][] roomData) {
        if (x < 0 || x >= WIDTH_FEN) {
            return true;
        }

        if (y < 0 || y >= HEIGHT_FEN) {
            return true;
        }

        float xi = x / TILES_SIZE;
        float yi = y / TILES_SIZE;

        int val = roomData[(int) yi][(int) xi];

        // 11 car c'est l'indice du carré transparent
        if (val >= 48 || val < 0 || val != 11) {
            return true;
        }
        return false;
    }

    public static float GoToWall(Rectangle2D.Float hitbox, float dx) {
        int square = (int) (hitbox.x / TILES_SIZE);
        if (dx > 0) {
            int squareNextX = square * TILES_SIZE;
            int diff = (int) (TILES_SIZE - hitbox.width);

            return (squareNextX + diff - 1);
        }
        return square * TILES_SIZE;
    }

    public static float GoToFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int square = (int) (hitbox.y / TILES_SIZE);
        if (airSpeed > 0) {
            // Tomber
            int squareNextY = square * TILES_SIZE;
            int diff = (int) (TILES_SIZE - hitbox.height);

            return (squareNextY + diff - 1);
        }
        // Sauter
        return square * TILES_SIZE;
    }

    public static boolean OnGround(Rectangle2D.Float hitbox, int[][] roomData) {
        if (!Collision(hitbox.x, hitbox.y + hitbox.height + 1, roomData)) {
            if (!Collision(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, roomData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] roomData) {
        if (xSpeed > 0) {
            return Collision(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, roomData);
        }
        return Collision(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, roomData);
    }

    public static boolean IsTileSolid(int x, int y, int[][] roomData) {
        int val = roomData[y][x];
        // 11 car c'est l'indice du carré transparent
        if (val >= 48 || val < 0 || val != 11) {
            return true;
        }
        return false;
    }

    public static boolean NoObstacle(int[][] roomData, Rectangle2D.Float playerHitBox, Rectangle2D.Float ennemyHitBox,
            int tileY) {
        int playerXTile = (int) (playerHitBox.x / TILES_SIZE);
        int ennemyXTile = (int) (ennemyHitBox.x / TILES_SIZE);

        if (playerXTile > ennemyXTile) {
            for (int i = 0; i < playerXTile - ennemyXTile; i++) {
                if (IsTileSolid(ennemyXTile + i, tileY, roomData)) {
                    return false;
                }
                if (!IsTileSolid(ennemyXTile + i, tileY + 1, roomData)) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < ennemyXTile - playerXTile; i++) {
                if (IsTileSolid(playerXTile + i, tileY, roomData)) {
                    return false;
                }
                if (!IsTileSolid(playerXTile + i, tileY + 1, roomData)) {
                    return false;
                }
            }
        }
        return true;
    }
}
