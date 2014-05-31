package gui.controller;

import gui.EditorGameView;
import gui.properties.ObjectPropertiesPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import logic.Game;
import logic.GameStructure;
import logic.Pos;
import logic.objects.DynamicObject;
import logic.objects.GameObject;

public class MouseInputGameView extends MouseInputAdapter implements MouseWheelListener {

    protected EditorGameView view;

    public MouseInputGameView(EditorGameView view) {
        if (view == null) {
            throw new IllegalArgumentException();
        }

        this.view = view;
    }

    private int preX;
    private int preY;

    private int distanceToX;
    private int distanceToY;

    private void selectObject(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null) {
            GameObject obj = gameStructure.getCurrentLevel()
                    .getObject(e.getX(), e.getY());
            view.setSelectedObject(obj);

            if (obj != null) {
                distanceToX = e.getX() - obj.getPos().getX();
                distanceToY = e.getY() - obj.getPos().getY();
                
                view.getFrame().changePropertiesPanel(new ObjectPropertiesPanel(view.getFrame(), obj));
            }
        }
    }
    
    private void addObject(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null && view.getObjectToAdd() != null) {
            GameObject obj;
            try {
                obj = view.getObjectToAdd().copy();
                obj.setPos(new Pos(e.getX(), e.getY()));
                gameStructure.getCurrentLevel().addObject(obj);
                view.setSelectedObject(obj);
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void addPlayer(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null && view.getObjectToAdd() != null) {
            DynamicObject obj;
            try {
                obj = (DynamicObject) view.getObjectToAdd().copy();
                obj.setPos(new Pos(e.getX(), e.getY()));
                gameStructure.getCurrentLevel().setPlayer(obj);
                gameStructure.getPlayerController().setControlledObject(obj);
                
                view.setSelectedObject(obj);
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void scaleObject(int scaleRatio) {
        int width = view.getSelectedObject().getWidth();
        int height = view.getSelectedObject().getHeight();
        view.getSelectedObject().setWidth((int)(width * (1 + 0.2 * scaleRatio)));
        view.getSelectedObject().setHeight((int)(height * (1 + 0.2 * scaleRatio)));
    }

    private void moveSelectedObj(MouseEvent e) {
        view.getSelectedObject().setPos(new Pos(
                e.getX() - distanceToX,
                e.getY() - distanceToY));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        preX = e.getX();
        preY = e.getY();

        if (SwingUtilities.isLeftMouseButton(e)) {
            selectObject(e);
        } else if(SwingUtilities.isRightMouseButton(e)) { 
            addObject(e);
        } else if(SwingUtilities.isMiddleMouseButton(e)) {
            addPlayer(e);
        }

        view.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        view.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (view.getSelectedObject() != null) {
                moveSelectedObj(e);
            }
        } 

        view.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        super.mouseWheelMoved(e);
        
        scaleObject(e.getWheelRotation());
        
        view.repaint();
    }
}
