package gui;

import gui.controler.MouseInputGameView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import logic.Game;
import logic.objects.GameObject;
import view.GameView;

public class EditorGameView extends GameView {

    protected GameObject selectedObject;
    
    private void initControler() {
        MouseInputGameView mouseList = new MouseInputGameView(this);
        
        this.addMouseListener(mouseList);
        this.addMouseMotionListener(mouseList);
    }
    
    public EditorGameView() {
        setPreferredSize(new Dimension(widthDefault, heightDefault));

        initControler();        
    }

    public EditorGameView(Game game) {
        this.game = game;
        setPreferredSize(game.getWindowSize());

        initControler();
    }

    public GameObject getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(GameObject obj) {
        selectedObject = obj;
    }
    
    @Override
    public void setGame(Game game) {
        super.setGame(game);
        selectedObject = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //add paint frame to selected object
        if(selectedObject != null) {
            g.setColor(Color.WHITE);
            g.drawRect(
                    selectedObject.getPos().getX()-1, 
                    selectedObject.getPos().getY()-1, 
                    selectedObject.getWidth()+2,
                    selectedObject.getHeight()+2);
            g.drawRect(
                    selectedObject.getPos().getX()+1, 
                    selectedObject.getPos().getY()+1, 
                    selectedObject.getWidth()-2,
                    selectedObject.getHeight()-2);
            
            g.setColor(Color.BLACK);
            g.drawRect(
                    selectedObject.getPos().getX(), 
                    selectedObject.getPos().getY(), 
                    selectedObject.getWidth(),
                    selectedObject.getHeight());
        }
    }
}
