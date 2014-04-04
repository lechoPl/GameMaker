package logic;

import java.io.Serializable;

public class Game implements Serializable {

    // constants
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = ".gmf";

    // fields
    private String gameName;

    // constructors
    public Game(String gameName) {
        this.gameName = gameName;
    }

    // setters and getters
    public void setName(String name) {
        this.gameName = name;
    }

    public String getName() {
        return gameName;
    }
}
