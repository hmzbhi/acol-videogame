package src.tools;
import static src.tools.Constants.Fenetre.*;

public class Constants {

    public static class Fenetre {
        // Dimensions du tableau de la fenêtre
        public static final float SCALE = 1.5f;
        public static final int TILES_DEFAULT_SIZE = 32;
        public static final int TILES_WIDTH = 26;
        public static final int TILES_HEIGHT = 14;
        public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);

        public static final int WIDTH_FEN = TILES_WIDTH * TILES_SIZE;
        public static final int HEIGHT_FEN = TILES_HEIGHT * TILES_SIZE;
    }

    public static class ObjectConstants {

        public static final int KEY = 0;
        public static final int DOOR = 1;
        public static final int CHEST = 2;

        public static final int KEY_WIDTH_DEFAULT = 24;
        public static final int KEY_HEIGHT_DEFAULT = 24;
        public static final int KEY_WIDTH = (int) (KEY_WIDTH_DEFAULT * SCALE);
        public static final int KEY_HEIGHT = (int) (KEY_HEIGHT_DEFAULT * SCALE);

        public static int GetObjectAnimation(int objectType) {
            switch (objectType) {
                case KEY:
                    return 8;
                default:
                    return 0;
            }
        }
    }

    public static class EnemyConstants {
        public static final int PIG = 0;

        public static final int DO_NOTHING = 0;
        public static final int RUNNING = 1;
        public static final int DYING = 2;
        public static final int ATTACK = 3;
        public static final int HURT = 4;

        public static final int PIG_WIDTH_DEFAULT = 34;
        public static final int PIG_HEIGHT_DEFAULT = 28;

        public static final int PIG_WIDTH = (int) (PIG_WIDTH_DEFAULT * SCALE);
        public static final int PIG_HEIGHT = (int) (PIG_HEIGHT_DEFAULT * SCALE);

        public static final int PIG_DRAW_X = (int) (11 * SCALE);
        public static final int PIG_DRAW_Y = (int) (10 * SCALE);

        public static int GetEnemyAnimation(int enemyType, int enemyState) {
            switch (enemyType) {
                case PIG:
                    switch (enemyState) {
                        case DO_NOTHING:
                            return 0;
                        case RUNNING:
                            return 1;
                        case DYING:
                            return 2;
                        case ATTACK:
                            return 3;
                        case HURT:
                            return 4;
                        default:
                            return 1;
                    }
            }
            return 0;
        }

        public static int MaxHealth(int enemyType) {
            switch (enemyType) {
                case PIG:
                    return 5;
                default:
                    return 1;
            }
        }

        public static int EnemyDamage(int enemyType) {
            switch (enemyType) {
                case PIG:
                    return 10;
                default:
                    return 0;
            }
        }
    }

    public static class PlayerConstants {

        public static final int RUNNING = 0;
        public static final int HURT = 1;
        public static final int DOOR_IN = 2;
        public static final int DYING = 3;
        public static final int ATTACK = 4;
        public static final int DO_NOTHING = 5;
        public static final int JUMP = 6;
        public static final int FALL = 7;

        public static int GetPlayerAnimation(int playerState) {
            switch (playerState) {
                case PlayerConstants.FALL:
                case PlayerConstants.JUMP:
                    return 1;
                case PlayerConstants.DYING:
                    return 4;
                case PlayerConstants.HURT:
                    return 2;
                case PlayerConstants.ATTACK:
                    return 3;
                case PlayerConstants.DO_NOTHING:
                    return 11;
                case PlayerConstants.DOOR_IN:
                case PlayerConstants.RUNNING:
                    return 8;
                default:
                    return 1;
            }
        }
    }

    public static class Directions {
        public static final int RIGHT = 0;
        public static final int LEFT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    
}
