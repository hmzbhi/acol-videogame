package src.main;

import java.awt.Graphics;

public class Game implements Runnable {

    private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS = 120;
    private final int UPS = 160;

    private Interaction interaction;

    public Game() {
        // init Classes
        this.interaction = new Interaction();

        this.gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startLoop();
    }

    private void startLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double tpf = 1000000000.0 / FPS;
        double tpu = 1000000000.0 / UPS;

        long prev = System.nanoTime();

        double dU = 0;
        double dF = 0;

        while (true) {
            long curr = System.nanoTime();

            dU += (curr - prev) / tpu;
            dF += (curr - prev) / tpf;
            prev = curr;

            if (dU >= 1) {
                update();
                dU--;
            }

            if (dF >= 1) {
                gamePanel.repaint();
                dF--;
            }
        }

    }

    public void update() {
        interaction.update();
    }

    public void gen(Graphics graphics) {
        interaction.gen(graphics);
    }

    public void windowFocusLost() {
        interaction.resetPlayerDirBool();
    }

    public Interaction getInteraction() {
        return this.interaction;
    }

    public Thread getgameThread() {
        return this.gameThread;
    }
}
