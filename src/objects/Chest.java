package src.objects;
import static src.tools.Constants.Fenetre.*;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import src.tools.LoadSave;

public class Chest extends GameObject{

    private BufferedImage chestImage;

    public Chest(int x, int y, int wichObj) {
        super(x, y, wichObj);
        initHitBox(9, 15);
        setDecY((int) (5 * SCALE));

        loadImages();
    }

    private void loadImages() {
        chestImage = LoadSave.getImageShape(LoadSave.CHEST + "Do-Nothing.png");
    }

    @Override
    protected void initHitBox(float width, float height) {
        hitBox = new Rectangle2D.Float(getX() + getDecX() + 13 * SCALE, getY() + getDecY() + 6 + 8 * SCALE,4 * width, 2 * height);
    }

    public Image getdoorImage() {
        return chestImage;
    }
    
}
