package logic;

import controller.PlayerController;
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
    
    public static int OBJ_ID_COUNT = 0;
    public static int LEVEL_ID_COUNT = 0;

    // fields
    private String gameName;
    private Color bgDafaultColor = Color.BLACK;

    protected Level currentLevel = null;
    private LinkedList<Level> levels;
    private LinkedList<Level> screens;
    
    private PlayerController playerContorller;

    // constructors
    public GameStructure() {
        init("Untitled game");
    }

    public GameStructure(String gameName) {
        init(gameName);
    }

    private void init(String gameName) {
        this.gameName = gameName;
        
        this.levels = new LinkedList();
        //levels.add(new Level("Level1"));
        //levels.add(new Level("Level2"));
        
        this.screens = new LinkedList();
        screens.add(new Level("Main menu"));
        screens.add(new Level("Start screen"));
        screens.add(new Level("Finish screen"));
    }

    public LinkedList<Level> getLevels() {
        return levels;
    }
    
    public Level getLevel(int id) {
        for(Level level : levels) {
            if(level.getId() == id)
                return level;
        }
        
        return null;
    }
    
    public LinkedList<Level> getScreens() {
        return screens;
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
    
    public PlayerController getPlayerController() {
        return playerContorller;
    }

    public void setPlayerController(PlayerController controller) {
        playerContorller = controller;
    }

    @Override
    public void render(Graphics g, GameResources gameResources) {
        if (currentLevel != null) {
            currentLevel.render(g, gameResources);
        }
    }
}
