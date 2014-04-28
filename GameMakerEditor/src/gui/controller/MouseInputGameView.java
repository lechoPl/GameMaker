package gui.controller;

import gui.EditorGameView;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputAdapter;
import logic.Game;
import logic.GameStructure;
import logic.Pos;
import logic.objects.GameObject;

public class MouseInputGameView extends MouseInputAdapter {

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
            }
        }
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

        if (e.getButton() == MouseEvent.BUTTON1) {
            selectObject(e);
        }

        view.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        view.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (view.getSelectedObject() != null) {
            moveSelectedObj(e);
        }

        view.repaint();
    }
}
