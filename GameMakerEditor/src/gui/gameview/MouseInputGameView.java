package gui.gameview;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import logic.Background;
import logic.GameStructure;
import logic.Pos;
import logic.objects.GameObject;

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

    protected Edge sellectedEdge;

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
            Point mousePoint = getMousePos(e);

            GameObject obj = getObject(e);

            view.setSelectedObject(obj);
            if (obj != null) {
                distanceToX = mousePoint.x - obj.getPos().getX();
                distanceToY = mousePoint.y - obj.getPos().getY();
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point mousePoint = getMousePos(e);

        preX = mousePoint.x;
        preY = mousePoint.y;

        if (SwingUtilities.isLeftMouseButton(e)) {
            selectObject(e);
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
}
