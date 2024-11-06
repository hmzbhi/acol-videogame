package src.main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import src.inputs.Keyboard;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

import static src.tools.Constants.Fenetre.*;

public class GamePanel extends JPanel {

    private Game game;
    private JFrame jframe;
    private Keyboard keyboard;

    public GamePanel(Game game) {
        this.game = game;
        this.keyboard = new Keyboard(this);

        setSize();

        addKeyListener(keyboard);

        // JFrame est l'interface graphique que nous utilisons
        jframe = new JFrame();

        // Congiguration de la fenêtre
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(this);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowLostFocus(WindowEvent e) {
                game.windowFocusLost();
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }
        });
    }

    private void setSize() {
        Dimension size = new Dimension(WIDTH_FEN, HEIGHT_FEN);
        setPreferredSize(size);
    }

    public void updateG() {

    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        game.gen(graphics);
    }

    public Game getGame() {
        return game;
    }
}