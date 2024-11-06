package src.elements;

import static src.tools.Constants.EnemyConstants.*;
import static src.tools.Constants.Directions.*;
import static src.tools.Methods.*;

import java.awt.geom.Rectangle2D;

import static src.tools.Constants.Fenetre.*;

public abstract class Enemy extends Element {

    protected float attackDist;

    protected int aniIndex;
    protected int aniSpeed;
    protected int aniTick;

    protected int state;
    protected int enemyType;

    protected boolean firstUpdate;

    protected float fallSpeed;
    protected float gravity;
    protected boolean flying;

    protected float walkSpeed;
    protected int walkDir;

    protected int tileY;

    protected int maxHealth;
    protected int currHealth;

    protected boolean alive;
    protected boolean attackCheck;

    public Enemy(float x, float y, float width, float height, int enemyType) {
        super(x, y, width, height);

        this.attackDist = TILES_SIZE;
        this.enemyType = enemyType;

        this.aniSpeed = 22;
        this.firstUpdate = true;

        this.fallSpeed = 0;
        this.gravity = 0.04f * SCALE;
        this.flying = false;

        this.walkSpeed = 0.5f * SCALE;
        this.walkDir = RIGHT;

        this.maxHealth = MaxHealth(enemyType);
        this.currHealth = maxHealth;
        this.alive = true;

        this.attackCheck = false;

        initHitBox(x, y, width, height);

    }

    public abstract void update(int[][] roomData, Player player);

    protected void updateAnimation() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetEnemyAnimation(enemyType, state)) {
                aniIndex = 0;

                switch (state) {
                    case ATTACK:
                    case HURT:
                        state = DO_NOTHING;
                        break;
                    case DYING:
                        alive = false;
                        break;
                }
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    };

    protected void firstUpdate(int[][] roomData) {
        if (!OnGround(getHitBox(), roomData)) {
            flying = true;
        }
        firstUpdate = false;
    }

    protected void uptadeFlying(int[][] roomData) {
        if (MoveAllowed(getHitBox().x, getHitBox().y + fallSpeed, getHitBox().width, getHitBox().height, roomData)) {
            getHitBox().y += fallSpeed;
            fallSpeed += gravity;
        } else {
            flying = false;
            getHitBox().y = GoToFloor(getHitBox(), fallSpeed);
            tileY = (int) (getHitBox().y / TILES_SIZE);
        }
    }

    protected void move(int[][] roomData) {
        float xSpeed = 0;
        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        if (MoveAllowed(getHitBox().x + xSpeed, getHitBox().y, getHitBox().width, getHitBox().height,
                roomData))
            if (IsFloor(getHitBox(), xSpeed, roomData)) {
                getHitBox().x += xSpeed;
                return;
            }
        changeWalkDir();
    }

    protected void newState(int state) {
        this.state = state;
        aniIndex = 0;
        aniTick = 0;
    }

    protected boolean playerInRange(Player player, int[][] roomData) {
        int playerTileY = (int) (player.getHitBox().y / TILES_SIZE);
        if (playerTileY == tileY) {
            if (isInRange(player)) {
                if (NoObstacle(roomData, player.getHitBox(), getHitBox(), tileY)) {
                    return true;
                }
            }
        }
        return false;

    }

    protected boolean isInRange(Player player) {
        return ((int) Math.abs(player.getHitBox().x - getHitBox().x) <= attackDist * 5);
    }

    protected void goToPlayerDir(Player player) {
        if (player.getHitBox().x > getHitBox().x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected boolean canAttackPlayer(Player player) {

        return ((int) Math.abs(player.getHitBox().x - getHitBox().x) <= attackDist);
    }

    public void dealDamage(int val) {
        currHealth -= val;
        if (currHealth <= 0) {
            newState(DYING);
        } else {
            newState(HURT);
        }
    }

    protected void checkPlayerHurt(Player player, Rectangle2D.Float attackZone) {
        if (attackZone.intersects(player.getHitBox())) {
            player.incrHealth(-EnemyDamage(enemyType));
        }
        attackCheck = true;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getAniSpeed(int aniSpeed) {
        return aniSpeed;
    }

    public int getAniTick() {
        return aniTick;
    }

    public int getState() {
        return state;
    }

    public int getEnemyType() {
        return enemyType;
    }

    public boolean isAlive() {
        return alive;
    }
}
