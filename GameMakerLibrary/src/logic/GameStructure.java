package logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import resources.GameResources;
import view.IViewable;

public class GameStructure implements IViewable, Serializable {

    public static final String GAME_PATH = "game.g";

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // fields
    private String gameName;
    private Color bgDafaultColor = Color.BLACK;

    protected Level currentLevel = null;
    private LinkedList<Level> levels;

    // constructors
    public GameStructure() {
        init("Untitled Game");
    }

    public GameStructure(String gameName) {
        init(gameName);
    }

    private void init(String gameName) {
        this.gameName = gameName;
        this.levels = new LinkedList();

        levels.add(new Level("Level1"));
        levels.add(new Level("Level2"));
    }

    public LinkedList<Level> getLevels() {
        return levels;
    }

    public void setCurrentLevel(Level lvl) {
        currentLevel = lvl;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void addNewLevel(Level lvl) {
        levels.add(lvl);
    }

    public Dimension getWindowSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public String getName() {
        return this.gameName;
    }

    public void setName(String name) {
        this.gameName = name;
    }

    public void setBgDefaultColor(Color c) {
        bgDafaultColor = c;
    }

    public Color getBgDefaultColor() {
        return bgDafaultColor;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        if (currentLevel != null) {
            currentLevel.render(g, gameResources);
        }
    }
}
