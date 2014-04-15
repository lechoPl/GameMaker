package logic;

import java.io.Serializable;
import java.util.LinkedList;

public class Game implements Serializable {

    // constants
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = ".gmf";

    // fields
    private String gameName;
    private LinkedList<Level> levels;

    // constructors
    public Game(String gameName) {
        this.gameName = gameName;
        
        this.levels = new LinkedList();
        levels.add(new Level("New level"));
        levels.add(new Level("New level"));
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
}
