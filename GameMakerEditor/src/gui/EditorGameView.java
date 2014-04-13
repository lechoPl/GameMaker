package gui;

import java.awt.Dimension;
import logic.Game;
import view.GameView;

public class EditorGameView extends GameView {
    public EditorGameView() {
        setPreferredSize(new Dimension(widthDefault, heightDefault));
    }
    
    public EditorGameView(Game game) {
        this.game = game;
        setPreferredSize(game.getWindowSize());
    }
    
    //add MouseInputListener
}