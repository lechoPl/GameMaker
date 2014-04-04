package logic;

import java.io.Serializable;

public class Level implements Serializable {

    // fields
    private String levelName;
    private int levelHeight;
    private int levelWidth;
    private Background levelBackground;

    // constructors
    public Level(String levelName) {
        this.levelName = levelName;
    }

    // setters and getters
    public void setName(String levelName) {
        this.levelName = levelName;
    }

    public String getName() {
        return this.levelName;
    }

    public void setHeight(int height) {
        this.levelHeight = height;
    }

    public int getHeight() {
        return this.levelHeight;
    }

    public void setWidth(int width) {
        this.levelWidth = width;
    }

    public int getWidth() {
        return this.levelWidth;
    }
    
    public void setBackground(Background background) {
        this.levelBackground = background;
    }
    
    public Background getBackground() {
        return this.levelBackground;
    }
}
