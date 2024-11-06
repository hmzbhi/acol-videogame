package src.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.main.GamePanel;

public class Keyboard implements KeyListener {

    private GamePanel gamePanel;

    public Keyboard(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                gamePanel.getGame().getInteraction().getPlayer().setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.getGame().getInteraction().getPlayer().setDown(false);
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getInteraction().getPlayer().setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getInteraction().getPlayer().setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getInteraction().getPlayer().setJump(false);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                gamePanel.getGame().getInteraction().getPlayer().setUp(true);
                break;
            case KeyEvent.VK_DOWN:
                gamePanel.getGame().getInteraction().getPlayer().setDown(true);
                break;
            case KeyEvent.VK_LEFT:
                gamePanel.getGame().getInteraction().getPlayer().setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                gamePanel.getGame().getInteraction().getPlayer().setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                gamePanel.getGame().getInteraction().getPlayer().setJump(true);
                break;
            case KeyEvent.VK_Z:
                gamePanel.getGame().getInteraction().getPlayer().setAttack(true);
                break;
            default:
                break;
        }
    }

}
