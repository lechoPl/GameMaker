package gui.gameview;

import gui.EditorFrame;
import gui.properties.DefaultPropertiesPanel;
import gui.properties.ObjectPropertiesPanel;
import gui.properties.PlayerPropertiesPanel;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    protected MousePopupMenu pMenu;

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

    private boolean freshSelect;

    protected Edge sellectedEdge;

    private Point getMousePos(MouseEvent e) {
        Point p = e.getPoint();

        if (view != null) {
            p = new Point(p.x - view.margin, p.y - view.margin);
        }
        pMenu = new MousePopupMenu(view);

        return p;
    }

    private void selectObject(MouseEvent e) {
        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null) {
            Point mousePoint = getMousePos(e);

            GameObject obj = getObject(e);
            EditorFrame frame = view.getFrame();

            refreshPropertiesPanel(obj);

            view.setSelectedObject(obj);
            if (obj != null) {
                distanceToX = mousePoint.x - obj.getPos().getX();
                distanceToY = mousePoint.y - obj.getPos().getY();
                freshSelect = true;
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

    private GameObject getObject(MouseEvent e) {
        GameObject obj = null;

        GameStructure gameStructure = view.getGame().getGameStructure();

        if (gameStructure != null && gameStructure.getCurrentLevel() != null) {

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
        }

        return obj;
    }

    private void moveSelectedObj(MouseEvent e) {
        if (view.getSelectedObject() != null) {
            Point mousePoint = getMousePos(e);

            view.getSelectedObject().setPos(new Pos(
                    mousePoint.x - distanceToX,
                    mousePoint.y - distanceToY));

            GameObject obj = view.getSelectedObject();
            refreshPropertiesPanel(obj);
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
                if (obj instanceof DynamicObject) {
                    gameStructure.getCurrentLevel().addMob((DynamicObject) obj);
                } else {
                    if (view.bgEdit) {
                        view.getGame().getGameStructure().getCurrentLevel().getBackground().addObject(obj);
                    } else {
                        view.getGame().getGameStructure().getCurrentLevel().addObject(obj);
                    }
                }
                view.setSelectedObject(obj);
                view.getFrame().refreshStructureTree();
            } catch (CloneNotSupportedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void resizeSelectedObj(MouseEvent e) {
        GameObject obj = view.getSelectedObject();

        if (obj == null || sellectedEdge == null) {
            return;
        }

        Point p = getMousePos(e);
        if (sellectedEdge == Edge.N || sellectedEdge == Edge.NE || sellectedEdge == Edge.NW) {
            int temp = obj.getPos().getY() - p.y;
            obj.setPos(new Pos(obj.getPos().getX(), obj.getPos().getY() - temp));
            obj.setHeight(obj.getHeight() + temp);
        }

        if (sellectedEdge == Edge.S || sellectedEdge == Edge.SE || sellectedEdge == Edge.SW) {
            int temp = p.y - obj.getPos().getY() - obj.getHeight();
            obj.setHeight(obj.getHeight() + temp);
        }

        if (sellectedEdge == Edge.W || sellectedEdge == Edge.SW || sellectedEdge == Edge.NW) {
            int temp = obj.getPos().getX() - p.x;
            obj.setPos(new Pos(obj.getPos().getX() - temp, obj.getPos().getY()));
            obj.setWidth(obj.getWidth() + temp);
        }

        if (sellectedEdge == Edge.E || sellectedEdge == Edge.NE || sellectedEdge == Edge.SE) {
            int temp = p.x - obj.getPos().getX() - obj.getWidth();
            obj.setWidth(obj.getWidth() + temp);
        }

        refreshPropertiesPanel(obj);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        freshSelect = false;
        Point mousePoint = getMousePos(e);

        preX = mousePoint.x;
        preY = mousePoint.y;

        selectObject(e);

        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
            System.out.print("klik");
            addObject(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            pMenu.show(e.getComponent(), e.getX(), e.getY(), getMousePos(e), freshSelect);
        }

        sellectedEdge = getObjectEdge(view.getSelectedObject(), mousePoint);

        view.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        sellectedEdge = null;

        view.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (view.getSelectedObject() != null) {

                Point p = getMousePos(e);
                if (sellectedEdge != null) {
                    resizeSelectedObj(e);
                } else {
                    moveSelectedObj(e);

                }

            }
        }

        view.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);

        Point p = getMousePos(e);

        view.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        GameObject obj = getObject(e);
        Edge edge = getObjectEdge(obj, p);
        view.setCursor(castEdgeToCursor(edge));
    }

    protected Edge getObjectEdge(GameObject obj, Point p) {

        int margin = 8;

        if (obj == null || p == null) {
            return null;
        }

        if (p.y - obj.getPos().getY() < margin) {

            if (p.x - obj.getPos().getX() < margin) {
                return Edge.NW;
            }

            if (obj.getPos().getX() + obj.getWidth() - p.x < margin) {
                return Edge.NE;
            }

            return Edge.N;
        }

        if (obj.getPos().getY() + obj.getHeight() - p.y < margin) {

            if (p.x - obj.getPos().getX() < margin) {
                return Edge.SW;
            }

            if (obj.getPos().getX() + obj.getWidth() - p.x < margin) {
                return Edge.SE;
            }

            return Edge.S;
        }

        if (p.x - obj.getPos().getX() < margin) {
            return Edge.W;
        }

        if (obj.getPos().getX() + obj.getWidth() - p.x < margin) {
            return Edge.E;
        }

        return null;
    }

    protected Cursor castEdgeToCursor(Edge e) {
        if (e == null) {
            return new Cursor(Cursor.DEFAULT_CURSOR);
        }

        switch (e) {
            case NW:
                return new Cursor(Cursor.NW_RESIZE_CURSOR);
            case N:
                return new Cursor(Cursor.N_RESIZE_CURSOR);
            case NE:
                return new Cursor(Cursor.NE_RESIZE_CURSOR);
            case E:
                return new Cursor(Cursor.E_RESIZE_CURSOR);
            case SE:
                return new Cursor(Cursor.SE_RESIZE_CURSOR);
            case S:
                return new Cursor(Cursor.S_RESIZE_CURSOR);
            case SW:
                return new Cursor(Cursor.SW_RESIZE_CURSOR);
            case W:
                return new Cursor(Cursor.W_RESIZE_CURSOR);
            default:
                return new Cursor(Cursor.DEFAULT_CURSOR);
        }
    }

    private void refreshPropertiesPanel(GameObject obj) {
        EditorFrame frame = view.getFrame();

        if (obj == null) {
            frame.changePropertiesPanel(new DefaultPropertiesPanel());
        } else if (obj == view.getFrame().getGame().getGameStructure().getCurrentLevel().getPlayer()) {
            frame.changePropertiesPanel(new PlayerPropertiesPanel(frame, obj));
        } else {
            frame.changePropertiesPanel(new ObjectPropertiesPanel(frame, obj, false));
        }
    }
}
