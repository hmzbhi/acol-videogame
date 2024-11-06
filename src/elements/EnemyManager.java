package src.elements;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import src.main.Interaction;
import src.tools.LoadSave;
import static src.tools.Constants.EnemyConstants.*;

public class EnemyManager {

    private ArrayList<Enemy> enemies;
    private Interaction interaction;

    public EnemyManager(Interaction interaction) {
        this.enemies = new ArrayList<Enemy>();
        this.interaction = interaction;
        addEnnemies();
    }

    private void addEnnemies() {
        LoadSave.addPigs(enemies, interaction.getNumberRoom());
    }

    public void update(int roomData[][], Player player) {
        for (Enemy e : enemies) {
            if (e instanceof Pig){
                Pig p = (Pig) e;
                p.update(roomData, player);
            }
        }
    }

    public void draw(Graphics graphics) {
        drawEnemies(graphics);
    }

    private void drawEnemies(Graphics graphics) {
        for (Enemy e : enemies) {
            if (e instanceof Pig){
                Pig p = (Pig) e;
                if (p.isAlive()) {
                    graphics.drawImage(p.getPigArray()[p.getState()][p.getAniIndex()],
                            (int) p.getHitBox().x - PIG_DRAW_X + p.reverseX() + 10 * (p.goRight()),
                            (int) p.getHitBox().y - PIG_DRAW_Y,
                            PIG_WIDTH * p.coefReverse(), PIG_HEIGHT, null);
                    // p.drawHitBox(graphics);
                    // p.drawAttackZone(graphics);
                }
            }
        }
    }

    public void checkEnemyHurt(Rectangle2D.Float attackZone) {
        for (Enemy e : enemies) {
            if (e instanceof Pig){
                Pig p = (Pig) e;
                if (p.isAlive()) {
                    if (attackZone.intersects(p.getHitBox())) {
                        p.dealDamage(10);
                        return;
                    }
                }
            }
        }
    }

    public void restart() {
        enemies.clear();
        addEnnemies();
    }

}
