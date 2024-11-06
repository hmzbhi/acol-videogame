package src.room;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static src.tools.Constants.Fenetre.*;

import src.main.Interaction;
import src.tools.LoadSave;

public class RoomManager {

    private BufferedImage[] roomShape;

    private ArrayList<Room> rooms;

    private Interaction interaction;

    public RoomManager(Interaction interaction) {
        this.interaction = interaction;
        importRoomShape();
        this.rooms = new ArrayList<Room>();
        for (int i = 0; i < LoadSave.NB_ROOM_CREATED; i++) {
            rooms.add(new Room(LoadSave.getRoomData(i)));
        }
    }

    private void importRoomShape() {
        BufferedImage image = LoadSave.getImageShape(LoadSave.ROOM_1_IMAGE);
        roomShape = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                roomShape[index] = image.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics graphics) {
        for (int i = 0; i < TILES_HEIGHT; i++) {
            for (int j = 0; j < TILES_WIDTH; j++) {
                int index = getCurrRoom().getShapeIndex(j, i);
                graphics.drawImage(roomShape[index], j * TILES_SIZE, i * TILES_SIZE, TILES_SIZE,
                        TILES_SIZE, null);
            }
        }
    }

    public void update() {

    }

    public Room getCurrRoom() {
        return rooms.get(interaction.getNumberRoom());
    }
}
