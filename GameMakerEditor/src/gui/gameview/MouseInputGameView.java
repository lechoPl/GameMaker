package gui.gameview;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import logic.Background;
import logic.GameStructure;
import logic.Pos;
import logic.objects.DynamicObject;
import logic.objects.GameObject;
import logic.objects.StaticObject;

public class MouseInputGameView extends MouseInputAdapter implements MouseWheelListener {

    protected LevelPreview view;

    public MouseInputGameView(LevelPreview view) {
        if (view == null) {
            throw new IllegalArgumentException();
        }

        this.view = view;
    }

    private int preX;
    private int preY;

    private int distanceToX;
    private int distanceToY;

    private Point getMousePos(MouseEvent e) {
        Point p = e.getPoint();

        if (view != null) {
            p = new Point(p.x - view.margin, p.y - view.margin);
        }

        return p;
    }

    private void selectObject(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null) {
            GameObject obj = null;

            Point mousePoint = getMousePos(e);

            if (!view.getBackgroundEditable()) {
                obj = gameStructure.getCurrentLevel()
                        .getObject(mousePoint.x, mousePoint.y);
            } else {
                Background bg = gameStructure.getCurrentLevel().getBackground();

                if (bg != null) {
                    obj = bg.getObject(mousePoint.x, mousePoint.y);
                }
            }

            view.setSelectedObject(obj);
            if (obj != null) {
                distanceToX = mousePoint.x - obj.getPos().getX();
                distanceToY = mousePoint.y - obj.getPos().getY();
            }
        }
    }

    private void addObject(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();
        Point mousePoint = getMousePos(e);

        if (gameStructure != null && gameStructure.getCurrentLevel() != null && view.getObjectToAdd() != null) {
            GameObject obj;
            try {
                obj = view.getObjectToAdd().copy();
                obj.setPos(new Pos(mousePoint.x, mousePoint.y));
                if(obj instanceof DynamicObject)
                    gameStructure.getCurrentLevel().addMob((DynamicObject) obj);
                else
                    gameStructure.getCurrentLevel().addObject((StaticObject) obj);
                view.setSelectedObject(obj);
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void addPlayer(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();
        Point mousePoint = getMousePos(e);

        if (gameStructure != null && gameStructure.getCurrentLevel() != null && view.getObjectToAdd() != null) {
            DynamicObject obj;
            try {
                obj = (DynamicObject) view.getObjectToAdd().copy();
                obj.setPos(new Pos(mousePoint.x, mousePoint.y));
                gameStructure.getCurrentLevel().setPlayer(obj);
                gameStructure.getPlayerController().setControlledObject(obj);

                view.setSelectedObject(obj);
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void scaleObject(int scaleRatio) {
        if (view.getSelectedObject() == null) {
            return;
        }

        int width = view.getSelectedObject().getWidth();
        int height = view.getSelectedObject().getHeight();
        view.getSelectedObject().setWidth((int) (width * (1 + 0.2 * scaleRatio)));
        view.getSelectedObject().setHeight((int) (height * (1 + 0.2 * scaleRatio)));
    }

    private void moveSelectedObj(MouseEvent e) {
        if (view.getSelectedObject() != null) {
            Point mousePoint = getMousePos(e);

            view.getSelectedObject().setPos(new Pos(
                    mousePoint.x - distanceToX,
                    mousePoint.y - distanceToY));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point mousePoint = getMousePos(e);

        preX = mousePoint.x;
        preY = mousePoint.y;

        if (SwingUtilities.isLeftMouseButton(e)) {
            if(e.getClickCount() == 1)
                selectObject(e);
            else if(e.getClickCount() == 2)
                addObject(e);
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
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
