package src.objects;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.tools.LoadSave;

import static src.tools.Constants.Fenetre.*;

public class Door extends GameObject {

    private BufferedImage doorImage;

    public Door(int x, int y, int wichObj) {
        super(x, y, wichObj);
        initHitBox(6, 15);
        setDecX((int) (9 * SCALE));
        setDecY((int) (5 * SCALE));
        setAlive(false);

        loadImages();
    }

    private void loadImages() {
        doorImage = LoadSave.getImageShape(LoadSave.DOOR + "Do-Nothing.png");
    }

    @Override
    protected void initHitBox(float width, float height) {
        hitBox = new Rectangle2D.Float(getX() + getDecX() + 13 * SCALE, getY() + getDecY() + 6 + 8 * SCALE,
                4 * width, 2 * height);
    }

    public Image getdoorImage() {
        return doorImage;
    }

}
