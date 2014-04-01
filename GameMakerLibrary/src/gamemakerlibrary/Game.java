package gamemakerlibrary;

import java.io.Serializable;

public class Game implements Serializable {
    public static final String FILE_EXTENSION = "gmf";
    public static final String FILE_EXTENSION_DOT = ".gmf";

    private Background background;

    public Background getBackground() {
        return this.background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }
}
