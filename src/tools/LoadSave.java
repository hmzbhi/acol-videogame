package src.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import src.elements.Enemy;
import src.elements.Pig;
import javax.imageio.ImageIO;

import src.objects.Chest;
import src.objects.Door;
import src.objects.GameObject;
import src.objects.Key;

import static src.tools.Constants.EnemyConstants.PIG;
import static src.tools.Constants.Fenetre.*;

public class LoadSave {

    public static final String KING_DO_NOTHING = "/res/kinghuman/Do-Nothing.png";
    public static final String KING_RUNNING = "/res/kinghuman/Running.png";
    public static final String KING_DYING = "/res/kinghuman/Dying.png";
    public static final String KING_ATTACK = "/res/kinghuman/Attack.png";
    public static final String KING_HURT = "/res/kinghuman/Hurt.png";
    public static final String KING_FALL = "/res/kinghuman/Falling.png";
    public static final String KING_DOOR_IN = "/res/kinghuman/Door-In.png";
    public static final String KING_JUMP = "/res/kinghuman/Jump.png";

    public static final String ROOM_1_IMAGE = "/res/room.png";

    public static final String PIG_DO_NOTHING = "/res/pig/Do-Nothing.png";
    public static final String PIG_RUNNING = "/res/pig/Running.png";
    public static final String PIG_DYING = "/res/pig/Dying.png";
    public static final String PIG_ATTACK = "/res/pig/Attack.png";
    public static final String PIG_HURT = "/res/pig/Hurt.png";

    public static final String PLAYER_LIFE = "/res/Points-de-vie.png";

    public static final String KEY = "/res/key/";
    public static final String HEART = "/res/heart/";
    public static final String DOOR = "/res/door/";
    public static final String CHEST = "/res/chest/";
    
    public static final int NB_ROOM_CREATED = 2;

    public static BufferedImage getImageShape(String file) {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream(file);

        try {
            image = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static int[][] getRoomData(int currentRoom) {
        int[][] roomData = new int[TILES_HEIGHT][TILES_WIDTH];
        BufferedImage image = getImageShape("/res/room-" + currentRoom + "-data.png");

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int val = color.getRed();
                if (val >= 48) {
                    val = 0;
                }
                roomData[i][j] = val;
            }
        }

        return roomData;
    }

    public static void addPigs(ArrayList<Enemy> enemies, int currentRoom) {

        BufferedImage image = getImageShape("/res/room-" + currentRoom + "-data.png");

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int val = color.getGreen();
                if (val == PIG) {
                    enemies.add(new Pig(j * TILES_SIZE, i * TILES_SIZE));
                }
            }
        }
    }

    public static void getKeys(ArrayList<GameObject> objects, int currentRoom) {
        BufferedImage image = getImageShape("/res/room-" + currentRoom + "-data.png");

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int val = color.getBlue();
                if (val == 0) {
                    objects.add(new Key(j * TILES_SIZE, i * TILES_SIZE, 0));
                }
            }
        }
    }

    public static void getDoors(ArrayList<GameObject> objects, int currentRoom) {
        BufferedImage image = getImageShape("/res/room-" + currentRoom + "-data.png");

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int val = color.getBlue();
                if (val == 1) {
                    objects.add(new Door(j * TILES_SIZE, i * TILES_SIZE, 0));
                }
            }
        }
    }

    public static void getChests(ArrayList<GameObject> objects, int currentRoom) {
        BufferedImage image = getImageShape("/res/room-" + currentRoom + "-data.png");

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                Color color = new Color(image.getRGB(j, i));
                int val = color.getBlue();
                if (val == 2) {
                    objects.add(new Chest(j * TILES_SIZE, i * TILES_SIZE, 0));
                }
            }
        }
    }
}
