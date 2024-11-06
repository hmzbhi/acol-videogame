package src.objects;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import src.main.Interaction;
import src.tools.LoadSave;
import static src.tools.Constants.ObjectConstants.*;

public class ObjectManager {

    private Interaction interaction;

    private ArrayList<GameObject> objects;

    private int numberKeys;
    private int currNumberKeys;

    public boolean apply;

    public ObjectManager(Interaction interaction) {
        this.interaction = interaction;
        this.objects = new ArrayList<GameObject>();
        addObjects();

        this.numberKeys = getKeysInObjects();
        this.apply = false;
    }

    private int getKeysInObjects() {
        int res = 0;
        for (GameObject go : objects){
            if (go instanceof Key){
                res += 1;
            }
        }
        return res;
    }

    private void addObjects() {
        LoadSave.getDoors(objects, interaction.getNumberRoom());
        LoadSave.getKeys(objects, interaction.getNumberRoom());
        LoadSave.getChests(objects, interaction.getNumberRoom());

    }

    public void update() {
        for (GameObject go : objects){
            if (go instanceof Key){
                Key key = (Key) go;
                if (key.isAlive()) {
                    key.update();
                }
            }
            if (go instanceof Door){
                Door d = (Door) go;
                if (d.isAlive()) {
                    d.update();
                }
            }

            if (go instanceof Chest){
                Chest c = (Chest) go;
                if (c.isAlive()) {
                    c.update();
                }
            }
        }
    }

    public void draw(Graphics graphics) {
        drawObjects(graphics);
    }

    private void drawObjects(Graphics graphics) {
        for (GameObject go : objects){
            if (go instanceof Key){
                Key key = (Key) go;
                if (key.isAlive()) {
                    graphics.drawImage(key.getkeyImages()[key.getAniIndex()], (int) (key.getHitBox().x - key.getDecX()),
                            (int) (key.getHitBox().y - key.getDecY()), KEY_WIDTH, KEY_HEIGHT, null);
                    // key.drawHitBox(graphics);
                }
            }

            if (go instanceof Door){
                Door d = (Door) go;
                if (d.isAlive()) {
                    graphics.drawImage(d.getdoorImage(), (int) (d.getHitBox().x - d.getDecX()),
                            (int) (d.getHitBox().y - d.getDecY()), KEY_WIDTH, KEY_HEIGHT, null);
                    // d.drawHitBox(graphics);
                }
            }

            if (go instanceof Chest){
                Chest c = (Chest) go;
                if (c.isAlive()) {
                    graphics.drawImage(c.getdoorImage(), (int) (c.getHitBox().x - c.getDecX()),
                            (int) (c.getHitBox().y - c.getDecY()), KEY_WIDTH, KEY_HEIGHT, null);
                    //c.drawHitBox(graphics);
                }
            }
        }
    }

    public void checkPlayerTouched(Rectangle2D.Float hitBox) {

        for (GameObject go : objects){
            if (go instanceof Key){
                Key key = (Key) go;
                if (key.isAlive()) {
                    if (hitBox.intersects(key.getHitBox())) {
                        applyKeysEffect();
                        key.setAlive(false);
                    }
                }
            }

            if (go instanceof Door){
                Door d = (Door) go;
                if (d.isAlive()) {
                    if (hitBox.intersects(d.getHitBox())) {
                        apply = true;
                    }
                }
            }

            if (go instanceof Chest){
                Chest c = (Chest) go;
                if (c.isAlive()) {
                    if (hitBox.intersects(c.getHitBox())) {
                        applyChestEffect();
                    }
                }
            }
        }
        if (apply){
            applyDoorEffect();
            apply = false;
        }
    }

    private void applyChestEffect() {
        interaction.setGameWin(true);
    }

    private void applyDoorEffect() {
       interaction.changeRoom();
    }

    public void applyKeysEffect() {
        currNumberKeys += 1;
        if (currNumberKeys >= numberKeys) {
            for (GameObject go : objects){
                if (go instanceof Door){
                    Door d = (Door) go;
                    d.setAlive(true);
                }
            }
        }
    }

    public int getNumberKeys() {
        return numberKeys;
    }

    public void restart() {
        objects.clear();
        addObjects();
        numberKeys = getKeysInObjects();
    }
}
