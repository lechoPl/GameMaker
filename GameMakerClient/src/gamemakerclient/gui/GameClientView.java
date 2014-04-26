package gamemakerclient.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import logic.Game;
import logic.Level;
import managers.GameFileManager;
import view.GameView;

public class GameClientView extends JPanel implements Runnable {

    private CustomGameView gamePanel = new CustomGameView();

    private GameFileManager gameFileManager = new GameFileManager();

    public GameClientView() {
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        Box box = new Box(BoxLayout.Y_AXIS);

        box.add(Box.createVerticalGlue());
        box.add(gamePanel);
        box.add(Box.createVerticalGlue());

        this.add(box);
    }

    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    double lastTime = System.nanoTime();
    int currentFPS = 60;

    /**
     * Game main loop
     */
    @Override
    public void run() {
        double currentTime;
        double delta; // last frame time

        int frameCount = 0;
        double time = 0;

        while (true) {
            currentTime = System.nanoTime();
            delta = currentTime - lastTime;

            // --- calculate fps
            time += delta;
            frameCount++;

            if (frameCount >= 5) {
                currentFPS = (int) ((1000000000 / time) * frameCount);
                time = 0;
                frameCount = 0;
            }
            // ---

            gamePanel.repaint();

            while (System.nanoTime() - currentTime  <= TARGET_TIME_BETWEEN_RENDERS) {
                Thread.yield();
                
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                }
            }

            lastTime = currentTime;
        }
    }

    public void setGame(Game g) {
        gamePanel.setGame(g);
    }

    public void showFPS(boolean val) {
        gamePanel.setShowFPS(val);
    }

    private class CustomGameView extends GameView {

        protected boolean showFPS = false;

        public CustomGameView() {

            //for tests
            createSampleGame();
        }

        @Override
        public synchronized void setGame(Game game) {
            super.setGame(game);

            if (game != null) {
                Dimension dim = game.getWindowSize();

                setPreferredSize(dim);
                setMaximumSize(dim);
                setMinimumSize(dim);
            }
        }

        public void setShowFPS(boolean val) {
            showFPS = val;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (showFPS) {
                g.setColor(Color.RED);
                g.drawString("FPS: " + currentFPS, 5, 15);
            }
        }

        private void createSampleGame() {
            Game gameTemp = new Game();

            Level level = Level.getSampleLevel();
            gameTemp.addNewLevel(level);
            gameTemp.setCurrentLevel(level);

            this.setGame(gameTemp);
        }
    }
}
