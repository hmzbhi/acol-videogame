package src.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.main.Interaction;
import src.tools.LoadSave;

import static src.tools.Constants.PlayerConstants.*;
import static src.tools.Constants.Fenetre.*;
import src.tools.Methods;
import static src.tools.Methods.*;

public class Player extends Element {

    private BufferedImage[][] animations;
    private Interaction interaction;

    private int aniTick;
    private int aniSpeed;
    private int aniIndex;

    private int playerAction;
    private float playerSpeed;

    private boolean move;
    private boolean attack;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean jump;
    private boolean flying;
    private boolean leftOriented;
    private boolean dying;

    private int reverseX;
    private int coefReverse;

    private int[][] roomData;

    private float jumpSpeed;
    private float fallSpeed;
    private float airSpeed;
    private float gravity;

    BufferedImage life;

    private int barWidth;
    private int barHeight;
    private int barX;
    private int barY;

    private int healthWidth;
    private int healthHeight;
    private int healthStartX;
    private int healthStartY;

    private int maxHealth;
    private int currHealth;
    private int currWidth;

    private Rectangle2D.Float attackZone;
    private boolean attackCheck;

    public Player(float x, float y, int width, int height, Interaction interaction) {
        super(x, y, width, height);
        this.interaction = interaction;
        loadAnimation();

        this.move = false;
        this.attack = false;

        this.aniTick = 0;
        this.aniSpeed = 22;
        this.aniIndex = 0;

        this.playerAction = DO_NOTHING;
        this.playerSpeed = 1.0f * SCALE;

        this.left = false;
        this.right = false;
        this.up = false;
        this.down = false;
        this.jump = false;
        this.flying = false;
        this.leftOriented = false;
        this.dying = false;

        this.reverseX = 0;
        this.coefReverse = 1;

        this.airSpeed = 0;
        this.fallSpeed = 0.5f * SCALE;
        this.jumpSpeed = -2.25f * SCALE;
        this.gravity = 0.04f * SCALE;

        this.barWidth = (int) (192 * SCALE);
        this.barHeight = (int) (58 * SCALE);
        this.barX = (int) (10 * SCALE);
        this.barY = (int) (10 * SCALE);

        this.healthWidth = (int) (150 * SCALE);
        this.healthHeight = (int) (4 * SCALE);
        this.healthStartX = (int) (34 * SCALE);
        this.healthStartY = (int) (14 * SCALE);
        this.maxHealth = 100;
        this.currHealth = 100;
        this.currWidth = healthWidth;

        this.attackZone = new Rectangle2D.Float(getX(), getY(), (int) (20 * SCALE), (int) (20 * SCALE));
        this.attackCheck = false;

        initHitBox(x, y, 16 * SCALE, 28 * SCALE);

    }

    public void updateAttackZone() {
        if (right) {
            attackZone.x = getHitBox().x + getHitBox().width + (int) (10 * SCALE);
        } else if (left) {
            attackZone.x = getHitBox().x - getHitBox().width - (int) (10 * SCALE);
        }
        attackZone.y = getHitBox().y + (int) (SCALE * 10);
    }

    public void initRoomData(int[][] roomData) {
        this.roomData = roomData;
        if (!OnGround(getHitBox(), roomData)) {
            flying = true;
        }
    }

    public void update() {
        updateHealth();
        updateAttackZone();
        updateCoord();
        if (move) {
            interaction.checkObjectTouched(getHitBox());
        }
        if (attack) {
            checkAttack();
        }
        updateAnimation();
        whichAnimation();
    }

    private void checkAttack() {
        if (attackCheck || aniIndex != 1) {
            return;
        }
        attackCheck = true;
        interaction.checkEnemyHurt(attackZone);
    }

    private void updateHealth() {
        currWidth = (int) (((float) currHealth / (float) maxHealth) * healthWidth);
    }

    private int isOrientedLeft() {
        if (leftOriented) {
            return 1;
        }
        return 0;
    }

    public void gen(Graphics graphics) {
        graphics.drawImage(animations[playerAction][aniIndex],
                (int) (getHitBox().x - 18 * SCALE) + reverseX - 18 * isOrientedLeft(),
                (int) (getHitBox().y - 17 * SCALE), ((int) getWidth()) * coefReverse, (int) getHeight(), null);
        // hitbox Player
        // drawHitBox(graphics);

        // hitbox AttackZone
        // graphics.setColor(Color.BLUE);
        // graphics.drawRect((int) attackZone.x, (int) attackZone.y, (int)
        // attackZone.width, (int) attackZone.height);

        // Points de vie
        graphics.drawImage(life, barX, barY, barWidth, barHeight, null);
        graphics.setColor(new Color(220, 73, 73));
        graphics.fillRect(healthStartX + barX, healthStartY + barY, currWidth, healthHeight);
    }

    private void loadAnimation() {
        animations = new BufferedImage[8][11];

        BufferedImage doNothing = LoadSave.getImageShape(LoadSave.KING_DO_NOTHING);
        BufferedImage running = LoadSave.getImageShape(LoadSave.KING_RUNNING);
        BufferedImage dying = LoadSave.getImageShape(LoadSave.KING_DYING);
        BufferedImage attack = LoadSave.getImageShape(LoadSave.KING_ATTACK);
        BufferedImage hurt = LoadSave.getImageShape(LoadSave.KING_HURT);
        BufferedImage doorIn = LoadSave.getImageShape(LoadSave.KING_DOOR_IN);
        BufferedImage falling = LoadSave.getImageShape(LoadSave.KING_FALL);
        BufferedImage jump = LoadSave.getImageShape(LoadSave.KING_JUMP);

        for (int i = 0; i < 8; i++) {
            animations[0][i] = running.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 2; i++) {
            animations[1][i] = hurt.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 8; i++) {
            animations[2][i] = doorIn.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 4; i++) {
            animations[3][i] = dying.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 3; i++) {
            animations[4][i] = attack.getSubimage(i * 78, 0, 78, 58);
        }
        for (int i = 0; i < 11; i++) {
            animations[5][i] = doNothing.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 1; i++) {
            animations[6][i] = jump.getSubimage(i * 78, 0, 78, 58);
        }

        for (int i = 0; i < 1; i++) {
            animations[7][i] = falling.getSubimage(i * 78, 0, 78, 58);
        }

        life = LoadSave.getImageShape(LoadSave.PLAYER_LIFE);
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    private void updateCoord() {
        move = false;

        if (jump) {
            jumping();
        }

        if (left || right || flying) {

            float dx = 0;

            if (left) {
                dx -= playerSpeed;
                reverseX = (int) getWidth();
                coefReverse = -1;
            } else if (right) {
                dx += playerSpeed;
                reverseX = 0;
                coefReverse = 1;
            }

            if (!flying) {
                if (!OnGround(getHitBox(), roomData)) {
                    flying = true;
                }
            }

            if (flying) {
                if (Methods.MoveAllowed(getHitBox().x, getHitBox().y + airSpeed, getHitBox().width, getHitBox().height,
                        roomData)) {
                    getHitBox().y += airSpeed;
                    airSpeed += gravity;
                    updateAbsCoord(dx);
                } else {
                    getHitBox().y = GoToFloor(getHitBox(), airSpeed);
                    if (airSpeed > 0) {
                        flying = false;
                        airSpeed = 0;
                    } else {
                        airSpeed = fallSpeed;
                    }
                    updateAbsCoord(dx);
                }
            } else {
                updateAbsCoord(dx);
            }
            move = true;
        }
    }

    private void jumping() {
        if (!flying) {
            flying = true;
            airSpeed = jumpSpeed;
        }
    }

    private void updateAbsCoord(float dx) {
        if (Methods.MoveAllowed(getHitBox().x + dx, getHitBox().y, getHitBox().width, getHitBox().height, roomData)) {
            getHitBox().x += dx;
        } else {
            getHitBox().x = GoToWall(getHitBox(), dx);
        }
    }

    private void whichAnimation() {
        int startAnimation = playerAction;

        if (dying) {
            playerAction = DYING;
        }

        if (move) {
            playerAction = RUNNING;
        } else {
            playerAction = DO_NOTHING;
        }
        if (flying) {
            if (airSpeed > 0) {
                playerAction = JUMP;
            }
            playerAction = RUNNING;
        }
        if (attack) {
            playerAction = ATTACK;
        }

        if (startAnimation != playerAction) {
            aniTick = 0;
            aniIndex = 0;
        }
    }

    private void updateAnimation() {
        aniTick++;

        if (aniTick >= aniSpeed) {
            aniIndex++;
            aniTick = 0;
            if (aniIndex >= GetPlayerAnimation(playerAction)) {
                aniIndex = 0;
                attack = false;
                attackCheck = false;
            }
        }
    }

    public void resetDirBool() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
        this.leftOriented = true;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
        this.leftOriented = false;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void incrHealth(int val) {
        currHealth += val;

        if (currHealth <= 0) {
            currHealth = 0;
            dying = true;

        } else if (currHealth >= 100) {
            currHealth = 100;
        }
    }

    public void restart() {
        initRoomData(interaction.getRoomManager().getCurrRoom().getRoomData());
    }

    public int getHealth() {
        return currHealth;
    }
}
