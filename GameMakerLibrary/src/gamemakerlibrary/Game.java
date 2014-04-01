package gamemakerlibrary;

import java.io.Serializable;

public class Game implements Serializable {
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = ".gmf";

    private String gameName;
    private Background background;
    
    public Game(String gameName) {
        this.gameName = gameName;
    }

    public Background getBackground() {
        return this.background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }
    
    public void setName(String name) {
        this.gameName = name;
    }
    
    public String getName() {
        return gameName;
    }
}
