package gamemakereditor;

import gui.EditorFrame;

public class GameMakerEditor {

    public static void main(String[] args) {
        EditorFrame mainFrame = new EditorFrame();
        if(args.length > 0) {
            mainFrame.loadGame(args[0]);
        }
    }
}
