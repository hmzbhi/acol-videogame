package src.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import src.elements.EnemyManager;
import src.elements.Player;
import src.objects.ObjectManager;
import src.room.RoomManager;
import static src.tools.Constants.Fenetre.*;

public class Interaction {

    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private RoomManager roomManager;

    private int currRoom;

    private Player player;

    private boolean gameWin;

    public Interaction() {
        this.enemyManager = new EnemyManager(this);
        this.roomManager = new RoomManager(this);
        this.objectManager = new ObjectManager(this);

        this.currRoom = 0;
        this.gameWin = false;

        this.player = new Player(200, 200, (int) (64 * SCALE), (int) (64 * SCALE), this);
        player.initRoomData(roomManager.getCurrRoom().getRoomData());

    }

    public void checkEnemyHurt(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHurt(attackBox);
    }

    public void checkObjectTouched(Rectangle2D.Float hitBox) {
        objectManager.checkPlayerTouched(hitBox);
    }

    public void changeRoom() {
        currRoom = currRoom + 1;
        objectManager.restart();
        enemyManager.restart();
        player.restart();
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public void update() {
        player.update();
        roomManager.update();
        objectManager.update();
        enemyManager.update(roomManager.getCurrRoom().getRoomData(), player);
    }

    public void gen(Graphics graphics) {
        roomManager.draw(graphics);
        objectManager.draw(graphics);
        player.gen(graphics);
        enemyManager.draw(graphics);

        if (player.getHealth() <= 0) {
            gameOver(graphics);
        }

        if (gameWin) {
            gameWin(graphics);
        }
    }

    private void gameOver(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0,WIDTH_FEN, HEIGHT_FEN);

        graphics.setColor(Color.WHITE);
        graphics.drawString("GAME OVER", WIDTH_FEN/2 , HEIGHT_FEN/2);
    }

    public void resetPlayerDirBool() {
        player.resetDirBool();
    }

    public Player getPlayer() {
        return player;
    }

    public int getNumberRoom() {
        return currRoom;
    }

    public void gameWin(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH_FEN, HEIGHT_FEN);

        graphics.setColor(Color.WHITE);
        graphics.drawString("YOU WIN", WIDTH_FEN/2, HEIGHT_FEN/2);
        
    }

    public void setGameWin(boolean b) {
        gameWin = b;
    }

}
