package view;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import logic.Game;
import logic.GameStructure;

public class GameView extends JPanel {
    protected int widthDefault = 100;
    protected int heightDefault = 100;
    
    protected int xLeftUpCorner;
    protected int yLeftUpCorner;
    
    protected Game game;
    
    public GameView() {
        setPreferredSize(new Dimension(widthDefault, heightDefault));
    }
    
    public GameView(Game game) {
        this.game = game;
        setPreferredSize(game.getGameStructure().getWindowSize());
    }
    
    public synchronized void setGame(Game game) { 
        this.game = game;
        this.setPreferredSize(game.getGameStructure().getWindowSize());
        this.repaint();
    }
    public Game getGame() { return this.game; }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(game.getGameStructure() != null)
            game.getGameStructure().render(g, game.getGameResources());
    }
}
