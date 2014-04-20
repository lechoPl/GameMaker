package logic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;
import view.IViewable;

public class Game implements Serializable, IViewable {
    private  static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
    // constants
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = ".gmf";

    // fields
    private String gameName;
    
    protected Level currentLevel = null;
    private LinkedList<Level> levels;

    // constructors
    public Game() {
        this("Untitled game");
    }
    
    public Game(String gameName) {
        this.gameName = gameName;
        
        this.levels = new LinkedList();
        
        levels.add(new Level("New level1"));
        levels.add(new Level("New level2"));
    }

    
    // setters and getters
    public void setName(String name) {
        this.gameName = name;
    }
    public String getName() {
        return gameName;
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
    
    @Override
    public void render(Graphics g) {
        if(currentLevel != null)
            currentLevel.render(g);
    }
}
