package src.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import static src.tools.Constants.ObjectConstants.*;

public abstract class GameObject {

    private int x;
    private int y;
    private int decX;
    private int decY;

    private int wichObj;
    protected Rectangle2D.Float hitBox;
    private boolean alive;

    private int aniTick;
    private int aniIndex;
    private int aniSpeed;

    public GameObject(int x, int y, int wichObj) {
        this.x = x;
        this.y = y;

        this.wichObj = wichObj;
        this.aniSpeed = 22;

        this.alive = true;
    }

    protected abstract void initHitBox(float width, float height);

    public void drawHitBox(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

    protected void update() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetObjectAnimation(wichObj)) {
                aniIndex = 0;
            }
        }
    }

    public void reset() {
        aniIndex = 0;
        aniTick = 0;
        alive = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDecX() {
        return decX;
    }

    public int getDecY() {
        return decY;
    }

    public void setDecX(int decX) {
        this.decX = decX;
    }

    public void setDecY(int decY) {
        this.decY = decY;
    }

    public int getWichObj() {
        return wichObj;
    }

    public void setWichObj(int wichObj) {
        this.wichObj = wichObj;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getAniIndex() {
        return aniIndex;
    }
}
