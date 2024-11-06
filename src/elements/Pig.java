package src.elements;

import static src.tools.Constants.Directions.RIGHT;
import static src.tools.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.tools.LoadSave;

import static src.tools.Constants.Fenetre.*;

public class Pig extends Enemy {

    private BufferedImage[][] pigArray;

    private Rectangle2D.Float attackZone;

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH, PIG_HEIGHT, PIG);
        initHitBox(x, y, 19 * SCALE, 14 * SCALE);
        loadEnemiesImages();

        this.attackZone = new Rectangle2D.Float(getX(), getY(), (int) (SCALE * 40), (int) (SCALE * 17));
    }

    @Override
    public void update(int[][] roomData, Player player) {
        updateMove(roomData, player);
        updateAnimation();
        updateAttackZone();
    }

    private void updateAttackZone() {
        if (walkDir == RIGHT) {
            attackZone.x = getHitBox().x;
        } else {
            attackZone.x = getHitBox().x - getHitBox().width;
        }
        attackZone.y = getHitBox().y;
    }

    public void drawAttackZone(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.drawRect((int) attackZone.x, (int) attackZone.y, (int) attackZone.width, (int) attackZone.height);
    }

    private void updateMove(int[][] roomData, Player player) {
        if (firstUpdate) {
            firstUpdate(roomData);
        }

        if (flying) {
            uptadeFlying(roomData);
        } else {
            switch (state) {
                case DO_NOTHING:
                    newState(RUNNING);
                    break;
                case RUNNING:
                    if (playerInRange(player, roomData)) {
                        goToPlayerDir(player);

                        if (canAttackPlayer(player)) {
                            state = ATTACK;
                        }
                    }
                    move(roomData);
                    break;
                case ATTACK:
                    if (aniIndex == 0) {
                        attackCheck = false;
                    }
                    if (aniIndex == 2 && !attackCheck) {
                        checkPlayerHurt(player, attackZone);
                    }
                    break;
                case HURT:
                    break;
            }
        }

    }

    private void loadEnemiesImages() {
        pigArray = new BufferedImage[5][11];

        BufferedImage doNothing = LoadSave.getImageShape(LoadSave.PIG_DO_NOTHING);
        BufferedImage running = LoadSave.getImageShape(LoadSave.PIG_RUNNING);
        BufferedImage dying = LoadSave.getImageShape(LoadSave.PIG_DYING);
        BufferedImage attack = LoadSave.getImageShape(LoadSave.PIG_ATTACK);
        BufferedImage hurt = LoadSave.getImageShape(LoadSave.PIG_HURT);

        for (int i = 0; i < 11; i++) {
            pigArray[0][i] = doNothing.getSubimage(i * 34, 0, 34, 28);
        }

        for (int i = 0; i < 6; i++) {
            pigArray[1][i] = running.getSubimage(i * 34, 0, 34, 28);
        }

        for (int i = 0; i < 4; i++) {
            pigArray[2][i] = dying.getSubimage(i * 34, 0, 34, 28);
        }

        for (int i = 0; i < 5; i++) {
            pigArray[3][i] = attack.getSubimage(i * 34, 0, 34, 28);
        }

        for (int i = 0; i < 2; i++) {
            pigArray[4][i] = hurt.getSubimage(i * 34, 0, 34, 28);
        }
    }

    public int reverseX() {
        if (walkDir == RIGHT) {
            return (int) getWidth();
        }
        return 0;
    }

    public int coefReverse() {
        if (walkDir == RIGHT) {
            return -1;
        }
        return 1;
    }

    public int goRight() {
        if (walkDir == RIGHT) {
            return 1;
        }
        return 0;
    }

    public BufferedImage[][] getPigArray(){
        return pigArray;
    }

}
