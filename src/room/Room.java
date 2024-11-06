package src.room;

public class Room {

    private int roomData[][];

    public Room(int[][] roomData) {
        this.roomData = roomData;
    }

    public int getShapeIndex(int x, int y) {
        return roomData[y][x];
    }

    public int[][] getRoomData() {
        return roomData;
    }
}
