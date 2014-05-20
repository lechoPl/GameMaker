package gamemakerclient.gui;

import controller.PlayerController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import logic.Game;
import logic.Level;
import view.GameView;

public class GameClientView extends JPanel implements Runnable {

    private CustomGameView gamePanel = new CustomGameView();

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
            //need to use keylistener
            this.setFocusOnGamePanel();

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

            //pull game controller
            this.getGame().pullControllers();

            //update game state
            double deltaInSecods = (double) delta / 10000000.0;
            this.getGame().getGameStructure().getCurrentLevel().update(deltaInSecods);

            //update game view
            gamePanel.repaint();

            //pause to get right fps
            while (System.nanoTime() - currentTime <= TARGET_TIME_BETWEEN_RENDERS) {
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

        if (g.getPlayerController() != null) {
            gamePanel.addKeyListener(g.getPlayerController());
        }
    }

    public void showFPS(boolean val) {
        gamePanel.setShowFPS(val);
    }

    public void setFocusOnGamePanel() {
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
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
                Dimension dim = game.getGameStructure().getWindowSize();

                setPreferredSize(dim);
                setMaximumSize(dim);
                setMinimumSize(dim);

                for (KeyListener oldKeyList : this.getKeyListeners()) {
                    this.removeKeyListener(oldKeyList);
                }

                this.addKeyListener(game.getPlayerController());
            }
        }

        public void setShowFPS(boolean val) {
            showFPS = val;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int xTranslate = 0;
            int yTranslate = 0;

            if (game.getGameStructure() != null) {

                Dimension size = this.getPreferredSize();
                int lvlWidth = game.getGameStructure().getCurrentLevel().getWidth();
                int lvlHeight = game.getGameStructure().getCurrentLevel().getHeight();

                if (size.width > lvlWidth) {
                    xTranslate = (size.width - lvlWidth) / 2;
                }

                if (size.height > lvlHeight) {
                    yTranslate = (size.height - lvlHeight) / 2;
                }

                g.setColor(game.getGameStructure().getBgDefaultColor());
                g.fillRect(-xTranslate, -yTranslate, size.width, yTranslate);
                g.fillRect(-xTranslate, lvlHeight, size.width, yTranslate);
                g.fillRect(-xTranslate, -yTranslate, xTranslate, size.height);
                g.fillRect(lvlWidth, -yTranslate, xTranslate, size.height);
            }

            if (showFPS) {
                g.setColor(Color.RED);
                g.drawString("FPS: " + currentFPS, 5 - xTranslate, 15 - yTranslate);
            }
        }

        private void createSampleGame() {
            Game gameTemp = new Game();
            gameTemp.getGameStructure().setBgDefaultColor(Color.GRAY);

            Level level = Level.getSampleLevel();
            gameTemp.getGameStructure().addNewLevel(level);
            gameTemp.getGameStructure().setCurrentLevel(level);

            PlayerController pc = new PlayerController();
            pc.setControlledObject(gameTemp.getGameStructure().getCurrentLevel().getPlayer());

            gameTemp.setPlayerController(pc);
            this.addKeyListener(pc);

            this.setGame(gameTemp);
        }
    }

    public Game getGame() {
        return this.gamePanel.getGame();
    }
}
