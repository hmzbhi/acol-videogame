package src.objects;

import static src.tools.Constants.Fenetre.*;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.tools.LoadSave;

public class Key extends GameObject {

    private BufferedImage[] keyImages = new BufferedImage[8];

    public Key(int x, int y, int wichObj) {
        super(x, y, wichObj);
        initHitBox(6, 15);
        setDecX((int) (9 * SCALE));
        setDecY((int) (5 * SCALE));

        loadImages();
    }

    private void loadImages() {
        BufferedImage temp;
        for (int i = 0; i < keyImages.length; i++) {
            temp = LoadSave.getImageShape(LoadSave.KEY + (i + 1) + ".png");
            keyImages[i] = temp;
        }
    }

    @Override
    protected void initHitBox(float width, float height) {
        hitBox = new Rectangle2D.Float(getX() + getDecX() + 13 * SCALE, getY() + getDecY() + 8 * SCALE, 2 * width, 2 * height);
    }

    public Image[] getkeyImages() {
        return keyImages;
    }

}
