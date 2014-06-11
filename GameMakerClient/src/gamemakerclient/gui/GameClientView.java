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
import logic.objects.DynamicObject;
import logic.objects.EndPoint;
import view.Camera;
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

            EndPoint endPoint = this.getGame().getGameStructure().getCurrentLevel().checkEndPoint();
            if (endPoint != null) {
                if (endPoint.getNextLevelId() != null
                        && this.getGame().getGameStructure().getLevel(endPoint.getNextLevelId()) != null) {
                    Level oldLevel = this.getGame().getGameStructure().getCurrentLevel();
                    Level newLevel = this.getGame().getGameStructure().getLevel(endPoint.getNextLevelId());
                    newLevel.getPlayer().setLives(oldLevel.getPlayer().getLives());
                    this.getGame().getGameStructure().setCurrentLevel(newLevel);
                    this.getGame().getGameStructure().getPlayerController().setControlledObject(newLevel.getPlayer());
                } else {
                    gamePanel.setEndGame(true);
                    gamePanel.setEndGameText("YOU WIN!!!");
                }
            } else if (!gamePanel.getEndGame()) {
                DynamicObject player = this.getGame().getGameStructure().getCurrentLevel().getPlayer();
                if (player.getLives() <= 0) {
                    gamePanel.setEndGame(true);
                    gamePanel.setEndGameText("GAME OVER");
                } else {
                    this.getGame().getGameStructure().getCurrentLevel().update(deltaInSecods);
                }
            }
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
        protected boolean endGame = false;
        protected String endGameText = "";

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

            endGame = false;
        }

        public void setShowFPS(boolean val) {
            showFPS = val;
        }

        public void setEndGame(boolean val) {
            endGame = val;
        }

        public boolean getEndGame() {
            return endGame;
        }

        public void setEndGameText(String s) {
            endGameText = s;
        }

        @Override
        public void paintComponent(Graphics g) {

            Camera.setCamera(g, game.getGameStructure());

            super.paintComponent(g);

            int xTranslate = 0;
            int yTranslate = 0;

            // hide objects when they cross level size
            if (game.getGameStructure() != null
                    && game.getGameStructure().getCurrentLevel() != null) {

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

                if (game.getGameStructure().getCurrentLevel().getPlayer() != null) {
                    g.setColor(Color.RED);
                    String lives = "Player lives: " + game.getGameStructure().getCurrentLevel().getPlayer().getLives();
                    g.drawChars(lives.toCharArray(), 0, lives.length(), 15 - Camera.getTranslateX(), 20 - Camera.getTranslateY());
                }

                if (endGame) {
                    int popupWidth
                            = g.getFontMetrics().charsWidth(endGameText.toCharArray(), 0, endGameText.length());
                    int popupHeight = g.getFontMetrics().getHeight();
                    int margin = 5;
                    g.setColor(Color.BLACK);
                    g.fillRect(((lvlWidth - popupWidth - 2 * margin) / 2) - Camera.getTranslateX(),
                           ((lvlHeight - popupHeight - 2 * margin) / 2) - Camera.getTranslateY(),
                            popupWidth + margin * 2,
                            popupHeight + margin * 2);

                    g.setColor(Color.WHITE);
                    g.drawChars(
                            endGameText.toCharArray(), 0, endGameText.length(),
                            lvlWidth / 2 - Camera.getTranslateX(),
                            lvlHeight / 2 - Camera.getTranslateY());
                }
            }

            if (showFPS) {
                g.setColor(Color.RED);
                g.drawString("FPS: " + currentFPS, 15 - Camera.getTranslateX(), 35 - Camera.getTranslateY());
            }
        }

        private void createSampleGame() {
            Game gameTemp = new Game();
            gameTemp.getGameStructure().setBgDefaultColor(Color.GRAY);

            Level level1 = Level.getSampleLevel();
            gameTemp.getGameStructure().addNewLevel(level1);

            Level level = Level.getSampleLevel(level1.getId());
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
